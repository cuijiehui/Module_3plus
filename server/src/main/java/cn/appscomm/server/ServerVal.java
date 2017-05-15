package cn.appscomm.server;

/**
 * <pre>
 * ServerVal里面存的变量，是需要P模块赋值，然后自己再获取的一个变量类
 * 例如ServerManager需要用token，但Server模块并不知道，只有P模块才知道，
 * 所以P请求网络时，不要忘记实时更新token值，这样ServerManger才能获取到最新的值
 * </pre>
 */
public class ServerVal {
    public static String accessToken = "";
}
