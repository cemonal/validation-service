package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.port.MxRecordCheckerPort;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

@Component
public class MxRecordCheckerAdapter implements MxRecordCheckerPort {

    @Override
    public boolean hasMxRecord(Email email) {
        try {
            DirContext ctx = new InitialDirContext();
            Attributes attrs = ctx.getAttributes("dns:/" + email.getDomain(), new String[]{"MX"});
            return attrs.get("MX") != null;
        } catch (NamingException e) {
            return false;
        }
    }
}