plugins {
    id("com.cognifide.aem.bundle")
    id("com.cognifide.aem.package")
}

apply(from = rootProject.file("gradle/common.gradle.kts"))

description = "AEM Stubs - Core"

dependencies {
    compileOnly("com.github.tomakehurst:wiremock:2.21.0")
    compileOnly("com.icfolson.aem.groovy.console:aem-groovy-console:14.0.0")
}

aem {
    tasks {
        bundleCompose {
            javaPackage.set(project.group.toString())
            exportPackage("com.github.tomakehurst.wiremock.*")
            importPackage(
                    "!junit.framework",
                    "!org.junit.rules",
                    "!org.junit.runner",
                    "!org.junit.runners.model",
                    "!org.junit", "!org.junit.internal",
                    "!com.github.tomakehurst.wiremock.junit",
                    "!com.github.tomakehurst.wiremock.jetty92",
                    "!com.github.tomakehurst.wiremock.jetty94",
                    "!org.eclipse.jetty.servlets.gzip",
                    "*"
            )

            embedPackage("com.github.tomakehurst:wiremock:2.21.0",
                    "com.github.tomakehurst.wiremock",
                    "com.github.tomakehurst.wiremock.common",
                    "com.github.tomakehurst.wiremock.core",
                    "com.github.tomakehurst.wiremock.jetty9",
                    "com.github.tomakehurst.wiremock.servlet",
                    "com.github.tomakehurst.wiremock.verification.*",
                    "com.github.tomakehurst.wiremock.extension",
                    "com.github.tomakehurst.wiremock.extension.responsetemplating",
                    "com.github.tomakehurst.wiremock.admin",
                    "com.github.tomakehurst.wiremock.admin.model",
                    "com.github.tomakehurst.wiremock.admin.tasks",
                    "com.github.tomakehurst.wiremock.global",
                    "com.github.tomakehurst.wiremock.extension.responsetemplating.helpers",
                    "com.github.tomakehurst.wiremock.stubbing",
                    "com.github.tomakehurst.wiremock.recording",
                    "com.github.tomakehurst.wiremock.http",
                    "com.github.tomakehurst.wiremock.http.trafficlistener",
                    "com.github.tomakehurst.wiremock.jetty9",
                    "com.github.tomakehurst.wiremock.client",
                    "com.github.tomakehurst.wiremock.matching",
                    "com.github.tomakehurst.wiremock.security",
                    "com.github.tomakehurst.wiremock.standalone",
                    "com.github.tomakehurst.wiremock.http")

            embedPackage("org.eclipse.jetty:jetty-servlets:9.4.20.v20190813",
                    "org.eclipse.jetty.servlets")

            embedPackage("com.google.guava:guava:27.0.1-jre",
                    "com.google.common.base",
                    "com.google.common.base.internal",
                    "com.google.common.collect",
                    "com.google.common.net",
                    "com.google.common.escape",
                    "com.google.common.hash",
                    "com.google.common.io",
                    "com.google.common.graph",
                    "com.google.common.math",
                    "com.google.common.primitives",
                    "com.google.thirdparty.publicsuffix")


            embedPackage("org.checkerframework:checker-qual:2.11.0",
                    "org.checkerframework.framework.qual;version=2.11.0", "org.checkerframework.checker.nullness.qual")
            embedPackage("com.google.errorprone:error_prone_parent:2.3.4", "com.google.errorprone.annotations",
                    "com.google.errorprone.annotations.concurrent")
            embedPackage("com.google.guava:failureaccess:1.0.1",
                    "com.google.common.util.concurrent.internal"
            )
            embedPackage("xmlunit:xmlunit:1.6",
                    "org.custommonkey.xmlunit",
                    "org.custommonkey.xmlunit.examples",
                    "org.custommonkey.xmlunit.exceptions",
                    "org.custommonkey.xmlunit.jaxp13",
                    "org.custommonkey.xmlunit.util")
            embedPackage("org.xmlunit:xmlunit-core:2.6.3", "org.xmlunit.*")
            embedPackage("com.jayway.jsonpath:json-path:2.4.0",
                    "com.jayway.jsonpath",
                    "com.jayway.jsonpath.internal.*",
                    "com.jayway.jsonpath.internal.filter",
                    "com.jayway.jsonpath.internal.path",
                    "com.jayway.jsonpath.spi.cache",
                    "com.jayway.jsonpath.spi.json",
                    "com.jayway.jsonpath.spi.mapper")
            embedPackage("net.sf.jopt-simple:jopt-simple:5.0.4", "joptsimple.*")


            embedPackage("com.github.jknack:handlebars:4.1.2",
                    "com.github.jknack.handlebars",
                    "com.github.jknack.handlebars.cache",
                    "com.github.jknack.handlebars.context",
                    "com.github.jknack.handlebars.io",
                    "com.github.jknack.handlebars.internal.*",
                    "com.github.jknack.handlebars.helper"
            )
            embedPackage("org.apache.tapestry:tapestry-json:5.3.7",
                    "org.codehaus.jettison.*")

            embedPackage("org.codehaus.jettison:jettison:1.4.0",
                    "org.apache.tapestry5.json")

            embedPackage("net.minidev:json-smart:2.3",
                    "net.minidev.json",
                    "net.minidev.json.parser",
                    "net.minidev.asm",
                    "net.minidev.json.annotate",
                    "net.minidev.json.reader",
                    "net.minidev.asm.ex",
                    "net.minidev.json.writer")

            embedPackage("org.ow2.asm:asm:7.0",
                    "org.objectweb.asm")
            embedPackage("com.flipkart.zjsonpatch:zjsonpatch:0.4.9",
                    "com.flipkart.zjsonpatch")
        }
    }
}