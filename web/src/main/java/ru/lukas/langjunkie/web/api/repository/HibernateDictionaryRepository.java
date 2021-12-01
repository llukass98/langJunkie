package ru.lukas.langjunkie.web.api.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import org.springframework.stereotype.Repository;

import ru.lukas.langjunkie.web.api.model.Dictionary;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public class HibernateDictionaryRepository implements DictionaryRepository {

    private final SessionFactory sessionFactory;

    //language=SQL
    private final static String SQL_FIND_BY_WORD_AND_LANGUAGE =
            "SELECT id, language, word, name, link " +
                    "FROM dictionary WHERE word=:word AND language=:language";

    public HibernateDictionaryRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Dictionary> findByWordAndLanguage(String word, String language) {
        Session session = sessionFactory.openSession();
        NativeQuery<Dictionary> query = session.createSQLQuery(SQL_FIND_BY_WORD_AND_LANGUAGE);

        query.setParameter("word", word);
        query.setParameter("language", language);
        query.addEntity(Dictionary.class);

        List<Dictionary> queryResults = query.getResultList();
        session.close();

        return queryResults;
    }

    @Override
    public void saveAll(List<Dictionary> dictionaries) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (Dictionary dictionary : dictionaries) {
            session.persist(dictionary);
        }

        session.getTransaction().commit();
        session.close();
    }
}
