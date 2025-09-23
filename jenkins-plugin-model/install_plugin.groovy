import jenkins.model.Jenkins

def jenkins = Jenkins.get()
def pm = jenkins.pluginManager
def uc = jenkins.updateCenter

// Update the Update Center metadata
uc.updateAllSites()

// List of plugins with dependencies
def plugins = ["github", "mstest", "workflow-aggregator", "docker-build-publish"]

plugins.each { pluginName ->
    if (!pm.getPlugin(pluginName)) {
        def plugin = uc.getPlugin(pluginName)
        if (plugin) {
            def deployment = plugin.deploy(true) // true = include dependencies
            deployment.get() // wait for install
            println "Installed plugin: ${pluginName}"
        } else {
            println "Plugin not found in update center: ${pluginName}"
        }
    } else {
        println "Plugin already installed: ${pluginName}"
    }
}

// Restart Jenkins after installing plugins (optional)
// jenkins.restart()
