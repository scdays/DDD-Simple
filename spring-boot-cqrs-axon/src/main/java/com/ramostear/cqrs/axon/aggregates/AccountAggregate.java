package com.ramostear.cqrs.axon.aggregates;

import com.ramostear.cqrs.axon.commands.CreateAccountCommand;
import com.ramostear.cqrs.axon.commands.CreditMoneyCommand;
import com.ramostear.cqrs.axon.commands.DebitMoneyCommand;
import com.ramostear.cqrs.axon.events.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * @author: ramostear
 * @email: ramostear@163.com
 * @create-time: 2019/4/18 0018-17:46
 * @desc: [一句话说明该文件的功能]
 * @since: V 0.0.1
 * @company： www.taolicloud.com
 */
@Aggregate
@Getter
@Setter
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String id;

    private double accountBalance;

    private String currency;

    private String status;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command){
        AggregateLifecycle.apply ( new AccountCreatedEvent ( command.id,command.accountBalance,command.currency ) );
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event){
        this.id = event.id;
        this.accountBalance = event.accountBalance;
        this.currency = event.currency;
        this.status = String.valueOf ( Status.CREATED);
        AggregateLifecycle.apply ( new AccountActivatedEvent ( this.id,Status.ACTIVATED ) );
    }


    @EventSourcingHandler
    protected void on(AccountActivatedEvent event){
        this.status = String.valueOf ( event.status );
    }


    @CommandHandler
    protected void on(CreditMoneyCommand command){
        AggregateLifecycle.apply ( new MoneyCreditedEvent ( command.id,command.creditAmount,command.currency ) );
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent event){
        if(this.accountBalance < 0 & (this.accountBalance + event.creditAmount) >= 0){
            AggregateLifecycle.apply ( new AccountActivatedEvent ( this.id,Status.ACTIVATED ) );
        }
        this.accountBalance += event.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand command){
        AggregateLifecycle.apply ( new MoneyDebitedEvent ( command.id,command.debitAmount,command.currency ) );
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent event){
        if(this.accountBalance >=0 &(this.accountBalance - event.debitAmount) <0){
            AggregateLifecycle.apply ( new AccountHeldEvent ( this.id,Status.HOLD ) );
        }
        this.accountBalance -= event.debitAmount;
    }


    @EventSourcingHandler
    private void on(AccountHeldEvent event){
        this.status = String.valueOf ( event.status);
    }
}
