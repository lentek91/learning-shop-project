package pl.training.shop.view;

import static java.util.Arrays.asList;

import com.vaadin.flow.i18n.I18NProvider;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class TranslationProvider implements I18NProvider {

  private static final String BUNDLE_BASE_NAME = "translation";
  private static final Locale LOCALE_PL = new Locale("pl", "PL");
  private static final Locale LOCALE_EN = new Locale("en", "GB");

  @Override
  public List<Locale> getProvidedLocales() {
    return Collections.unmodifiableList(asList(LOCALE_PL, LOCALE_EN));
  }

  @Override
  public String getTranslation(String key, Locale locale, Object... params) {
    final ResourceBundle bundle = initializeBundle(locale);
    String value;
    try {
      value = bundle.getString(key);
    } catch (final MissingResourceException e) {
      log.warning("Missing resource");
      return "!" + locale.getLanguage() + ": " + key;
    }
    if (params.length > 0) {
      value = MessageFormat.format(value, params);
    }
    return value;
  }

  private static ResourceBundle initializeBundle(final Locale locale) {
    final ClassLoader cl = TranslationProvider.class.getClassLoader();
    ResourceBundle propertiesBundle = null;
    try {
      propertiesBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale, cl);
    } catch (final MissingResourceException e) {
      log.warning("Missing resource");
    }
    return propertiesBundle;
  }

}