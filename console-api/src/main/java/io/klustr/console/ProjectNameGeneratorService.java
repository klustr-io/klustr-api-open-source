package io.klustr.console;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.utils.RandomNameGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Generates random names for use for project names.
 * <p>
 * [
 * "mild-jaguar-dd7159",
 * "administrative-seahorse-ed9a17",
 * "critical-bovid-0e8558",
 * "miserable-crocodile-0e7405",
 * "marginal-stoat-d13fe5",
 * "impressed-stoat-3dc3c8",
 * "wee-lark-76ef95",
 * "spectacular-porpoise-e069b5",
 * "actual-constrictor-4385d7",
 * "artificial-flamingo-3c0d8c"
 * ]
 */
@RestController
@Component
@RequestMapping("/console")
@Tag(name = "Project Naming APIs", description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectNameGeneratorService {

    /**
     * Generates a collection of random names.
     *
     * @return A list of project IDs.
     * @apiNote My api note
     * @implNote My impl not
     * @since version 1.0
     */
    @GetMapping("/projects/ids")
    @Operation(
operationId = "generateDevelopmentProjectIds", summary = "Generate unique project IDs for development.",
            description = """
This endpoint generates a list of unique project IDs suitable for development use. The IDs are randomly created, ensuring they are distinct and can be utilized for new projects. This functionality simplifies the process of creating identifiers for your development initiatives.
""",
            hidden = true,
            responses = {
                    @ApiResponse(
                            description = "Default result",
                            useReturnTypeSchema = true,
                            content = {
                                    @Content(mediaType = "application/json", examples = {
                                            @ExampleObject(name = "example1", value =
                                                    """
                                                            [
                                                              "squealing-mink-99007b",
                                                              "perfect-amphibian-27fce7",
                                                              "planned-beaver-e51a91",
                                                              "immense-mink-75ab46",
                                                              "devoted-ape-01ea7a",
                                                              "running-hedgehog-95585d",
                                                              "envious-crow-fcd261",
                                                              "domestic-slug-f8bb75",
                                                              "primary-mackerel-65b1ef",
                                                              "evident-crayfish-f83bb7"
                                                            ]
                                                            """
                                            )
                                    })
                            }
                    )
            }
)
    public List<String> randomNames() throws Exception {
        return new RandomNameGenerator().randomAnimalsAndAdjectives(10);
    }

}
