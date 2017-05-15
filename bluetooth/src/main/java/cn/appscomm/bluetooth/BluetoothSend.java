package cn.appscomm.bluetooth;

import android.util.SparseArray;

import java.util.LinkedList;
import java.util.Map;

import cn.appscomm.bluetooth.mode.Device;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙发送类
 * 1、命令分了8个类型，分别有优先级
 */
public class BluetoothSend {
    private static final String TAG = BluetoothSend.class.getSimpleName();
    public static final int COMMAND_TYPE_BIND = 0;
    public static final int COMMAND_TYPE_CALL = 1;
    public static final int COMMAND_TYPE_PAGE = 2;
    public static final int COMMAND_TYPE_REMOTE_CONTROL = 3;
    public static final int COMMAND_TYPE_SYNC = 4;
    public static final int COMMAND_TYPE_NOTIFY = 5;
    public static final int COMMAND_TYPE_NORMAL = 6;
    public static final int COMMAND_TYPE_CONTACT = 7;

    private SparseArray<LinkedList<Leaf>> sendDataArray = new SparseArray<>();
    private LinkedList<Leaf> bindCommands = new LinkedList<>();                                     // 绑定命令集合
    private LinkedList<Leaf> callCommands = new LinkedList<>();                                     // 来电命令集合
    private LinkedList<Leaf> pageCommands = new LinkedList<>();                                     // 页面命令集合
    private LinkedList<Leaf> remoteControlCommands = new LinkedList<>();                            // 远程控制命令集合(例如音乐控制，拍照)
    private LinkedList<Leaf> syncCommands = new LinkedList<>();                                     // 同步命令集合
    private LinkedList<Leaf> notifyCommands = new LinkedList<>();                                   // 一般通知命令集合
    private LinkedList<Leaf> normalCommands = new LinkedList<>();                                   // 一般命令集合(常用于后台自动发送命令)
    private LinkedList<Leaf> contactCommands = new LinkedList<>();                                  // 通信录命令集合

    public BluetoothSend() {
        sendDataArray.put(0, bindCommands);
        sendDataArray.put(1, callCommands);
        sendDataArray.put(2, pageCommands);
        sendDataArray.put(3, remoteControlCommands);
        sendDataArray.put(4, syncCommands);
        sendDataArray.put(5, notifyCommands);
        sendDataArray.put(6, normalCommands);
        sendDataArray.put(7, contactCommands);
    }

    public static void addLeaf(Leaf leaf, int type, String[] macs) {
        if (macs == null || macs.length == 0) return;
        for (String mac : macs) {
            Device device = BluetoothLeService.deviceMap.get(mac);
            LogUtil.i(TAG, "新增一条命令:mac(" + mac + ") device(" + (device != null) + ")");
            if (device != null) {
                device.bluetoothSend.add(leaf, type);
            }
        }
    }

    public static void clearAllCommand(String[] macs) {
        for (String mac : macs) {
            Device device = BluetoothLeService.deviceMap.get(mac);
            if (device != null) {
                device.bluetoothSend.clearBindCommand();
            }
        }
    }

    /**
     * 新增要发送的命令
     *
     * @param leaf 命令
     * @param type 命令的类型
     * @return true：新增成功 false：新增失败
     */
    public boolean add(Leaf leaf, int type) {
        LogUtil.i(TAG, "新增要发送的命令" + (leaf != null));
        if (leaf != null) {
            switch (type) {
                case COMMAND_TYPE_BIND:
                    LogUtil.i(TAG, "新增一条绑定类的命令");
                    bindCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_CALL:
                    LogUtil.i(TAG, "新增一条来电类的命令");
                    callCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_PAGE:
                    LogUtil.i(TAG, "新增一条页面类的命令");
                    pageCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_REMOTE_CONTROL:
                    LogUtil.i(TAG, "新增一条远程控制类的命令");
                    remoteControlCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_SYNC:
                    LogUtil.i(TAG, "新增一条同步类的命令");
                    syncCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_NOTIFY:
                    LogUtil.i(TAG, "新增一条一般通知类的命令");
                    notifyCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_NORMAL:
                    LogUtil.i(TAG, "新增一条一般命令类的命令");
                    normalCommands.addLast(leaf);
                    return true;
                case COMMAND_TYPE_CONTACT:
                    LogUtil.i(TAG, "新增一条通信录类的命令");
                    contactCommands.addLast(leaf);
                    return true;
            }
        }
        return false;
    }

    /**
     * 按照优先级遍历集合
     *
     * @return 有命令需要发送的则返回需要发送的命令，没有则返回null
     */
    public Leaf getSendCommand() {
        for (int i = 0; i < sendDataArray.size(); i++) {
            LinkedList<Leaf> tempList = sendDataArray.get(i);
            if (tempList != null && tempList.size() > 0) {
                return tempList.getFirst();
            }
        }
        return null;
    }

    /**
     * 检查是否包含同步命令
     *
     * @return
     */
    public boolean checkContainSyncCommand() {
        return syncCommands != null && syncCommands.size() > 0;
    }

    /**
     * 清空绑定命令
     */
    public void clearBindCommand() {
        bindCommands.clear();
    }

    /**
     * 获取命令集合的大小
     *
     * @return 返回需要发送数据的大小
     */
    public int getSendDataSize() {
        return bindCommands.size() + callCommands.size() + pageCommands.size() + remoteControlCommands.size() + syncCommands.size() + notifyCommands.size() + normalCommands.size() + contactCommands.size();
    }

    /**
     * 清空命令集合
     */
    public void clear() {
        for (int i = 0; i < sendDataArray.size(); i++) {
            sendDataArray.get(i).clear();
        }
    }

    public Leaf removeFirst() {
        for (int i = 0; i < sendDataArray.size(); i++) {
            LinkedList<Leaf> tempList = sendDataArray.get(i);
            if (tempList != null && tempList.size() > 0) {
                return tempList.removeFirst();
            }
        }
        return null;
    }
}
