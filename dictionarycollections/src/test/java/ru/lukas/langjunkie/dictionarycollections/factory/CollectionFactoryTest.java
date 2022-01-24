package ru.lukas.langjunkie.dictionarycollections.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;

import javax.xml.crypto.KeySelectorException;

import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("CollectionFactory tests")
class CollectionFactoryTest {

    private static final CollectionFactory collectionFactory = new CollectionFactory();

    @Test
    @DisplayName("getCollection() should return the same instance when called more than once with the same param")
    void getCollectionShouldReturnTheSameInstanceWhenCalledMoreThanOnce() throws KeySelectorException {
        Collection faEnCollection = collectionFactory.getCollection(DictionaryCollection.FAEN);
        Collection enEnCollection = collectionFactory.getCollection(DictionaryCollection.ENEN);

        Collection faEnCollection2 = collectionFactory.getCollection(DictionaryCollection.FAEN);
        Collection enEnCollection2 = collectionFactory.getCollection(DictionaryCollection.ENEN);

        assertSame(faEnCollection, faEnCollection2);
        assertSame(enEnCollection, enEnCollection2);
    }
}