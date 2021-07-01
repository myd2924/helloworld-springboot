package com.myd.helloworld.jms.consumer.resolver.support;

import com.myd.helloworld.jms.consumer.resolver.MessageResolver;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:48
 * @Description:
 */
public class ResolverManager {

    private static final ResolverManager INSTANCE = new ResolverManager();

    @Getter(AccessLevel.PACKAGE)
    private MessageResolver[] resolvers = new MessageResolver[0];

    void register( final MessageResolver[] resolvers){
        this.resolvers = resolvers;
    }

    static ResolverManager getInstance(){
        return INSTANCE;
    }
}
