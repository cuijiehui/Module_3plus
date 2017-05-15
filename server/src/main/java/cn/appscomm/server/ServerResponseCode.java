package cn.appscomm.server;

import android.text.TextUtils;
import android.util.SparseArray;

import cn.appscomm.server.util.ServerAppContext;

/**
 * Created by Administrator on 2017/3/1.
 */
public class ServerResponseCode {

    public static final int RESPONSE_CODE_ERROR = -9999;                                            // 错误
    public static final int RESPONSE_CODE_ACCESS_TOKEN_EXPIRED = -9525;                             // token过期
    public static final int RESPONSE_CODE_ACCESS_TOKEN_INVALID = -9526;                             // token无效
    public static final int RESPONSE_CODE_ACCESS_TOKEN_NULL = -9527;                                // token为空
    public static final int RESPONSE_CODE_SUCCESS = 0;                                              // 请求成功

    private SparseArray<String> codeMessageMap;
    private static ServerResponseCode serverResponseCode = new ServerResponseCode();

    private ServerResponseCode() {
        String[] codeMessages = ServerAppContext.INSTANCE.getContext().getResources().getStringArray(R.array.s_response_code_message);
        if (codeMessageMap == null) {
            codeMessageMap = new SparseArray<>(codeMessages.length);
        }
        codeMessageMap.clear();
        for (int i = 0; i < codeMessages.length; i++) {
            String message = codeMessages[i];
            if (!TextUtils.isEmpty(message)) {
                String[] tips = message.split(",");
                if (tips.length == 2 && tips[0] != null) {
                    try {
                        int indexNum = Integer.parseInt(tips[0].trim());
                        codeMessageMap.append(indexNum, tips[1]);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
    }

    // 获取ServerResponseCode实例
    public static ServerResponseCode getInstance() {
        return serverResponseCode;
    }

    /**
     * 检查是否成功
     *
     * @param responseCode 响应码
     * @return true : 成功 ; false : 失败
     */
    public boolean checkResult(int responseCode) {
        return responseCode == RESPONSE_CODE_SUCCESS;
    }

    /**
     * 通过响应码返回响应消息
     *
     * @param responseCode 响应码
     * @return "" : 没有找到响应消息 ; 不为空则为响应消息
     */
    public String getResponseMessage(int responseCode) {
        return codeMessageMap == null ? "" : codeMessageMap.get(responseCode, "");
    }

    /**
     * 退出
     */
    public void onDestory() {
        if (codeMessageMap != null) {
            codeMessageMap.clear();
            codeMessageMap = null;
        }
    }


}
