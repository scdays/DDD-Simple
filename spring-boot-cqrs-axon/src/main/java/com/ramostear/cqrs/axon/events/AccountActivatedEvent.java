package com.ramostear.cqrs.axon.events;


import com.ramostear.cqrs.axon.aggregates.Status;

/**
 * @author: ramostear
 * @email: ramostear@163.com
 * @create-time: 2019/4/17 0017-16:31
 * @desc: [一句话说明该文件的功能]
 * @since: V 0.0.1
 * @company： www.taolicloud.com
 */
public class AccountActivatedEvent extends BaseEvent<String> {

    public final Status status;

    public AccountActivatedEvent(String id,Status status){
        super(id);
        this.status = status;
    }
}
