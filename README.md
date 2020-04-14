# controllerdemo
 写一个Restful接口很简单，但是要写出一个健壮而优雅的接口并不容易，通常一个接口包含输入参数、输出响应消息及接口中异常信息输出。通过对请求输入参数在入口处进行统一校验，可以提前发现数据的问题而减少业务层数据校验模板代码，规范统一的响应格式和异常信息使你的Restful接口变得更优雅。
####一、使用validation对输入参数进行校验
######1.1 在maven中pom文件中引入 validation
<!-- https://mvnrepository.com/artifact/javax.validation/validation-api -->
        <dependency>
            <groupId>javax.validation</groupId>
            <artifactId>validation-api</artifactId>
            <version>2.0.1.Final</version>
        </dependency>
######1.2 使用@NotEmpty和@NotNull注解对需要校验的实体属性进行标注，在注解的message属性加上提示信息。
      @Data
      public class Movie {
          private String id;
          @NotEmpty(message = "Movie name cannot be empty")
          private String name;
          @NotNull(message = "电影时长不能为空")
          private Integer duration;
          @NotNull(message = "演员不能为空")
          @NotEmpty(message = "演员不能为空")
          private List<@Valid Actor> actors;
          @NotEmpty(message = "电影描述不能为空")
          private String description;
          
      }
 ######1.3controller需要加上@Validated注解，接口中需要校验的参数前面加上@Valid 注解。如下面的addMovie方法中的实体Movie前面使用了@Valid直接，表示该restful接口收到请求后会对实体Movie中的属性进行校验(使用了@NotEmpty和@NotNull等注解标注的属性)
    @RestController
    @Validated
    @RequestMapping("/movies/")
    public class MovieController {
        @Resource
        private MovieService movieService;
    
    
        @PostMapping("addMovie")
        public ResponseResult addMovie(@RequestBody @Valid Movie movie){
            movieService.addMovie(movie);
            System.out.println("test");
            System.out.println(movie);
            return ResponseResult.success();
        }
    }
 #### 二. 使用统一的接口数据输出格式
 ###### 2.1 定义统一响应消息体
     @Data
     public class ResponseResult<T> {
         /**
          * 状态码
          */
         int code;
         /**
          * 描述信息
          */
         String message;
         /**
          * 接口返回数据
          */
         T data;
     
         private ResponseResult(){
             this(200,"success");
         }
     
         private ResponseResult(int code,String message){
             this.code = code;
             this.message = message;
         }
         private ResponseResult(StatusCode statusCode){
             this.code = statusCode.getCode();
             this.message = statusCode.getMessage();
         }
         private ResponseResult(int code,String message,T data){
             this.code = code;
             this.message = message;
             this.data = data;
         }
         private ResponseResult(StatusCode statusCode,T data){
             this.code = statusCode.getCode();
             this.message = statusCode.getMessage();
             this.data = data;
         }
         public static ResponseResult success(){
             return new ResponseResult();
         }
         public static <T> ResponseResult success(T data) {
             return success(StatusCode.SUCCESS.getCode(),"success",data);
         }
         public static ResponseResult success(int code,String message) {
             return success(code,message,null);
         }
         public static ResponseResult success(StatusCode statusCode) {
             return success(statusCode.getCode(),statusCode.getMessage(),null);
         }
     
         public static <T> ResponseResult success(StatusCode statusCode,T data) {
             return success(statusCode.getCode(),statusCode.getMessage(),data);
         }
     
         public static <T> ResponseResult success(int code,String message,T data) {
             return new ResponseResult(code,message,data);
         }
     
         public static ResponseResult fail() {
             return fail(StatusCode.FAIL.getCode(),StatusCode.FAIL.getMessage());
         }
     
         public static ResponseResult fail(int code,String message) {
             return fail(code,message,null);
         }
     
         public static ResponseResult fail(StatusCode statusCode) {
             return fail(statusCode.getCode(),statusCode.getMessage(),null);
         }
     
         public static <T> ResponseResult fail(StatusCode statusCode, T data) {
             return fail(statusCode.getCode(),statusCode.getMessage(),data);
         }
     
         public static <T> ResponseResult fail(int code,String message,T data) {
             return new ResponseResult(code,message,data);
         }
     }
 ######2.1 定义状态码枚举类
     @Getter
     public enum StatusCode {
         /**
          * 操作成功
          */
         SUCCESS(200,"success"),
         /**
          * 新增成功
          */
         ADD_SUCCESS(204,"success"),
         /**
          * 操作失败
          */
         FAIL(-1,"fail"),
         /**
          * 资源不存在
          */
         NOT_FOUND(404,"resource not found"),
         /**
          * 没有权限访问
          */
         NOT_AUTH(401,"没有权限访问"),
         /**
          * 未知错误
          */
         ERROR(500,"未知错误")
         ;
     
         private int code;
         private String message;
         private StatusCode(int code,String message){
             this.code = code;
             this.message = message;
         }
     }
 ####三. 定义异常处理信息
 ######3.1 自定义异常类
     @Data
     public class BusinessException extends RuntimeException {
         private String message;
         private Throwable throwable;
         public BusinessException(String message){
             this(message,null);
         }
         public BusinessException(String message,Throwable throwable){
             super(message,throwable);
         }
     }
 ######3.2 定义异常拦截类
 程序中抛出的所有异常都统一被拦截器处理然后统一输出，这样避免了输入不友好的输出信息。也可以根据不同业务定义不同的异常，在此统一处理后输出。
 ####四. 测试
 ######4.1 正常访问成功
     入参： 
     {
            "id":"0001",
            "name":"外星人来了",
            "duration":116,
            "actors":[{
                "name":"赵丽颖",
                "age":"41"
            },{
                "name":"张亮",
                "age":"37"
            }],
            "description":"抵抗外星人入侵"
         }
     输出：
     {
         "code": 200,
         "message": "success",
         "data": null
     }
 ######4.2 异常输出
       入参: {
            "id":"0001",
            "name":"",
            "duration":116,
            "actors":[{
                "name":"赵丽颖",
                "age":"41"
            },{
                "name":"张亮",
                "age":"37"
            }],
            "description":"抵抗外星人入侵"
           }
       输出：
       {
           "code": 400,
           "message": "Movie name cannot be empty",
           "data": null
       }
