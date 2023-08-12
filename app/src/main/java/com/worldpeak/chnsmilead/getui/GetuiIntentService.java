package com.worldpeak.chnsmilead.getui;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * Create by ggyy on 12
 */
public class GetuiIntentService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int i) {
        super.onReceiveServicePid(context, i);
    }

    /**
     * 此方法用于接收和处理透传消息。透传消息个推只传递数据，不做任何处理，客户端接收到透传消息后需要自己去做后续动作处理，如通知栏展示、弹框等。
     * 如果开发者在客户端将透传消息创建了通知栏展示，建议将展示和点击回执上报给个推。
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        byte[] payload = msg.getPayload();
        String data = new String(payload);
        Log.d(TAG, "PUSH_LOG receiver payload = " + data);//透传消息文本内容

        //taskid和messageid字段，是用于回执上报的必要参数。详情见下方文档“6.2 上报透传消息的展示和点击数据”
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
//        Log.e(TAG, "PUSH_LOG onReceiveClientId -> " + "clientid = " + clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        //消息点击后回调
//        Log.i("PUSH_LOG", msg.getTitle()+ msg.getContent()+" "+msg.getMessageId());
    }

    /**
     * 上报个推透传消息的展示回执。如果透传消息本地创建通知栏消息“展示”了，则调用此方法上报。
     */
    public boolean pushGtShow(Context context, String taskid ,String messageid) {
        int gtactionid = 60001;//gtactionid传入60001表示个推渠道消息展示了
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, gtactionid);
        return result;
    }

    /**
     * 上报个推透传消息的点击回执。如果透传消息本地创建通知栏消息被“点击”了，则调用此方法上报。
     */
    public boolean pushGtClick(Context context, String taskid ,String messageid) {
        int gtactionid = 60002;//gtactionid传入60002表示个推渠道消息点击了
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, gtactionid);
        return result;
    }
}
