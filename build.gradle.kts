plugins {
    id("com.neva.fork")
    id("com.cognifide.aem.instance.local")
    id("net.researchgate.release")
    id("com.github.breadmoirai.github-release")
}

apply(from = "gradle/fork/props.gradle.kts")
apply(from = "gradle/common.gradle.kts")

description = "AEM Stubs"
defaultTasks(":assembly:wiremock-app:packageDeploy")

aem {
    instance {
        satisfier {
            packages {
                "tool.groovy-console"("https://github.com/icfnext/aem-groovy-console/releases/download/14.0.0/aem-groovy-console-14.0.0.zip")
            }
        }
        provisioner {
            step("enable-crxde") {
                sync {
                    osgi.configure("org.apache.sling.jcr.davex.impl.servlets.SlingDavExServlet", mapOf(
                            "alias" to "/crx/server"
                    ))
                }
            }
            step("enable-stubs-samples") {
                sync {
                    osgi.configure("com.cognifide.aem.stubs.core.script.ConfigurableStubScriptManager", mapOf(
                            "excluded.paths" to listOf("**/internals/*")
                    ))
                }
            }
        }
    }
}

githubRelease {
    owner("Cognifide")
    repo("gradle-aem-plugin")
    token((findProperty("github.token") ?: "").toString())
    tagName(project.version.toString())
    releaseName(project.version.toString())
    draft((findProperty("github.draft") ?: "false").toString().toBoolean())
    prerelease((findProperty("github.prerelease") ?: "false").toString().toBoolean())
    overwrite((findProperty("github.override") ?: "true").toString().toBoolean())

    gradle.projectsEvaluated {
        releaseAssets(listOf(
                ":assembly:all:packageCompose",
                ":assembly:app:packageCompose",
                ":assembly:wiremock-all:packageCompose",
                ":assembly:wiremock-app:packageCompose",
                ":assembly:moco-all:packageCompose",
                ":assembly:moco-app:packageCompose"
        ).map { rootProject.tasks.getByPath(it) })
    }

    body { """
    |# What's new
    |
    |TBD
    |
    |# Upgrade notes
    |
    |Nothing to do.
    |
    |# Contributions
    |
    |None.
    """.trimMargin()
    }
}

tasks {
    named("githubRelease") {
        mustRunAfter(release)
    }

    register("fullRelease") {
        dependsOn("release", "githubRelease")
    }
}
