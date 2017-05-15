package cn.appscomm.presenter.interfaces;

import cn.appscomm.server.mode.base.BaseResponse;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface PVServerCallback {

    /**
     * 请求类型集合(枚举)
     */
    enum RequestType {                                                                              // 请求类型
        LOGIN,                                                                                      // 登录
        REGISTER,                                                                                   // 注册
        ACCOUNT_QUERY,                                                                              // 账户查询
        ACCOUNT_EDIT,                                                                               // 账户编辑
        FORGET_PASSWORD,                                                                            // 忘记密码
        MODIFY_PASSWORD,                                                                            // 修改密码
        PAIR,                                                                                       // 配对
        UN_PAIR,                                                                                    // 解绑
        GET_PAIR_DEVICE,                                                                            // 获取绑定设备
        UPLOAD_IMAGE,                                                                               // 上传头像
        UPLOAD_SPORT_DATA,                                                                          // 上传运动数据
        GET_SPORT_DATA,                                                                             // 获取运动数据
        UPLOAD_SLEEP_DATA,                                                                          // 上传睡眠数据
        GET_SLEEP_DATA,                                                                             // 获取睡眠数据
        UPLOAD_HEART_RATE_DATA,                                                                     // 上传心率数据
        GET_HEART_RATE_DATA,                                                                        // 获取心率数据
        GET_FIRMWARE_VERSION,                                                                       // 获取固件版本
        DOWNLOAD_FIRMWARE,                                                                          // 下载固件
    }


    /**
     * 请求服务器成功
     *
     * @param requestType 请求类型(枚举类型)
     */
    void onSuccess(RequestType requestType);

    /**
     * 请求服务器成功(需要具体项目解析)
     *
     * @param baseResponse 返回信息的基类，UI拿到后可以强制转具体的类型
     * @param requestType  请求类型(枚举类型)
     */
    void onSuccess(BaseResponse baseResponse, RequestType requestType);

    /**
     * 请求服务器失败
     *
     * @param requestType  请求类型(枚举类型)
     * @param responseCode 响应码
     * @param message      响应消息
     */
    void onFail(RequestType requestType, int responseCode, String message);
}
