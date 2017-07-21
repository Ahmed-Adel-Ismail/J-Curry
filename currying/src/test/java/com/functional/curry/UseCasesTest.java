package com.functional.curry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Predicate;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ahmed Adel Ismail on 7/2/2017.
 */

public class UseCasesTest
{


    @Test
    public void translationsListUseCaseWithoutCurrying() throws Exception {

        // done in Object A
        final Translations translations = new Translations();

        // done in Object B
        List<String> matchedTranslations = Observable.fromArray("ordinateur", "ordinador", "kompjuter")
                .filter(byMatchingTranslation(translations))
                .toList().blockingGet();

        assertTrue(matchedTranslations.size() == 2);


    }

    private Predicate<String> byMatchingTranslation(final Translations translations) {
        return new Predicate<String>()
        {
            @Override
            public boolean test(@NonNull String word) throws Exception {
                return translations.contains(word);
            }
        };
    }


    @Test
    public void translationsListUseCaseWithCurrying() throws Exception {

        // done in object A
        Translations translations = new Translations();
        Predicate<String> byMatchingTranslation = Curry.apply(byMatchingTranslation(), translations);



        // done in Object B
        List<String> matchedTranslations = Observable.fromArray("ordinateur", "ordinador", "kompjuter")
                .filter(byMatchingTranslation)
                .toList().blockingGet();

        assertTrue(matchedTranslations.size() == 2);


    }

    private BiPredicate<Translations, String> byMatchingTranslation() {
        return new BiPredicate<Translations, String>()
        {
            @Override
            public boolean test(@NonNull Translations translations, @NonNull String word) {
                return translations.contains(word);
            }
        };
    }



}


class Translations extends ArrayList<String>{
    Translations() {
        add("computer");
        add("ordinateur");
        add("kompjuter");
    }
}
