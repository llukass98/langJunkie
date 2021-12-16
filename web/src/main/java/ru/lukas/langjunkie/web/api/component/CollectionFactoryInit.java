package ru.lukas.langjunkie.web.api.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;

/**
 * @author Dmitry Lukashevich
 */
@Component
public class CollectionFactoryInit {

    @Bean
    public CollectionFactory collectionFactory() {
        return new CollectionFactory();
    }
}
