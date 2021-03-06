package com.ramostear.cqrs.axon.controller;

import com.ramostear.cqrs.axon.dto.AccountCreatedDTO;
import com.ramostear.cqrs.axon.dto.MoneyCreditDTO;
import com.ramostear.cqrs.axon.dto.MoneyDebitDTO;
import com.ramostear.cqrs.axon.services.commands.AccountCommandService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * @author: ramostear
 * @email: ramostear@163.com
 * @create-time: 2019/4/18 0018-18:40
 * @desc: [一句话说明该文件的功能]
 * @since: V 0.0.1
 * @company： www.taolicloud.com
 */
@RestController
@RequestMapping(value="/bank-accounts")
@Api(value = "Account Commands",description = "Account Commands Related Endpoint",tags = "Account Commands")
public class AccountCommandController {

    private final AccountCommandService accountCommandService;

    public AccountCommandController(AccountCommandService accountCommandService){
        this.accountCommandService = accountCommandService;
    }


    @PostMapping
    public CompletableFuture<String> createAccount(@RequestBody AccountCreatedDTO dto){
        return accountCommandService.createAccount ( dto );
    }

    @PutMapping(value="/credits/{accountNumber}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountNumber") String accountNumber, @RequestBody MoneyCreditDTO dto){
        return accountCommandService.creditMoneyToAccount ( accountNumber,dto );
    }

    @PutMapping(value="/debits/{accountNumber}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountNumber")String accountNumber, @RequestBody MoneyDebitDTO dto){
        return accountCommandService.debitMoneyFromAccount ( accountNumber,dto );
    }




}
