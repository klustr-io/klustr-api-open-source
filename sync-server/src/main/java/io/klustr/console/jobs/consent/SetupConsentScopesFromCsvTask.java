package io.klustr.console.jobs.consent;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.klustr.setup.SetupTask;
import io.klustr.setup.SetupTaskRunnable;
import io.klustr.consent.ConsentStorage;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.consent.Text;
import io.klustr.utils.U;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
@SetupTask
public class SetupConsentScopesFromCsvTask implements SetupTaskRunnable {

    private final ConsentStorage consent_storage;

    public SetupConsentScopesFromCsvTask(ConsentStorage consent_storage) {
        this.consent_storage = consent_storage;
    }

    public void setup() {

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();

        try (InputStream is = U.getResourceAsStream("setup/scopes.csv", this)) {
            try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(is))
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build()) {

                String[] columns;
                while ((columns = csvReader.readNext()) != null) {
                    String category = columns[0];
                    String subcategory = columns[1];
                    String label = columns[2];
                    String id = columns[3];
                    String desc = columns[4];
                    String sensitivity = columns[5];
                    String visible = columns[6];

                    ConsentAttribute attr = new ConsentAttribute()
                            .withId(id)
                            .withOrgId("*") // everyone has these
                            .withCategory(category)
                            .withSubcategory(subcategory)
                            .withLabel(new Text().withEn(label))
                            .withDescription(new Text().withEn(desc))
                            .withSensitivity(ConsentAttribute.Sensitivity.fromValue(sensitivity))
                            .withVisible(Boolean.valueOf(visible));

                    Optional<ConsentAttribute> match = this.consent_storage.consent_scopes().tryGetObject(attr.getId());
                    if (match.isEmpty()) {
                        this.consent_storage.consent_scopes().insertObject(attr.getId(), attr);
                    } else {
                        this.consent_storage.consent_scopes().updateObject(attr.getId(), attr);
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean repeat() {
        return false;
    }
}
