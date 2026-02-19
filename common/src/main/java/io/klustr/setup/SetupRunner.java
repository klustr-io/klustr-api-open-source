package io.klustr.setup;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class SetupRunner {
    private static final Logger log = LoggerFactory.getLogger(SetupRunner.class);
    private final ApplicationContext context;
    private final SetupStateStorage state;

    public SetupRunner(ApplicationContext context, SetupStateStorage state) {
        this.context = context;
        this.state = state;
    }

    public synchronized List<SetupState> run() {
        Map<String, Object> beans = context.getBeansWithAnnotation(SetupTask.class);

        List<SetupState> results = Lists.newArrayList();

        beans.values().stream()
                .sorted(Comparator.comparingInt(
                        b -> b.getClass().getAnnotation(SetupTask.class).order()
                ))
                .forEach(bean -> {

                    String id = bean.getClass().getName();
                    log.info("===============================");
                    log.info("Running task {}...", id);
                    log.info("===============================");

                    SetupState object = this.state.state().getObject(id);
                    if (object != null) {
                        results.add(object);
                        return;
                    }
                    log.info("===============================");
                    log.info("✅ Task {} Completed", id);
                    log.info("===============================");

                    if (bean instanceof SetupTaskRunnable) {
                        try {
                            ((SetupTaskRunnable) bean).setup();
                            object = new SetupState(id);
                            this.state.state().insertObject(id, object);
                            results.add(object);
                            object = new SetupState(id);
                        } catch (Exception ex) {
                            log.error("Could not run setup.",ex);
                            log.error("===============================");
                            log.error("❌ Task {} Failed", id, ex);
                            log.error("===============================");
                        }
                    } else {
                        throw new IllegalStateException(
                                "SetupTask must implement SetupTaskRunnable: " + bean.getClass()
                        );
                    }
                });

        return results;
    }
}
