# Purpose

This is a template Maven project to build a Solr 7.x Query Parser plugin. The template creates a "myqp" (My Query Parser) query parser plugin that is implemented as a pass-through to the Lucene Classic Query Parser.

# Project Setup

## Solr

### Solr Binaries Download & Setup

All instructions are given relative to the project's root (i.e., from the location of this README.md file), and pertain to the setup of a stand-alone Solr node (i.e., not a Solr Cloud setup).

1. Download the latest Solr binaries (7.5.0 at the time of writing) from [Releases](http://lucene.apache.org/solr/downloads.html).
1. Unzip (or untar) the binaries in the project's root. In the remainder of the instructions, the Solr's installation directory is referred to as $SOLR_INSTALL. The unzipped binaries should reside under the solr-7.5.0 directory off the project's root.
1. Add the unzipped Solr directory and its content to the gitignored file.
1. In order to separate the Solr binaries from the application's Solr configuration, create a Solr "home" directory at the project's root: ```mkdir solr-home```. In the remainder of the instructions, Solr's home directory is referred as $SOLR_HOME
1. Under $SOLR_HOME, create a sub-directory for the collection. For readability, let's call it "collection1".
1. Copy into $SOLR_HOME the following files from $SOLR_INSTALL\server\solr:
    1. README.txt
    1. solr.xml
    1. zoo.cfg
1. Under $SOLR_HOME/collection1, create a file named "core.properties", and write the following line in it:
```name=collection1```
1. Copy $SOLR_INSTALL/server/solr/configsets/_default/conf to $SOLR_HOME/collection1.
1. For convenience in this project, we will switch from "managed" schema to "classic" schema, with the following instructions - See [Switching from Managed Schema to Manually Edited schema.xml](https://lucene.apache.org/solr/guide/6_6/schema-factory-definition-in-solrconfig.html#SchemaFactoryDefinitioninSolrConfig-SwitchingfromManagedSchematoManuallyEditedschema.xml). In $SOLR_HOME/collection1/conf:
    1. Rename managed-schema to schema.xml
    1. in solrconfig.xml, add the following line (After the <codec .../> line): ```<schemaFactory class="ClassicIndexSchemaFactory"/>```
1. Start Solr: ```./solr-7.5.0/bib/solr start -f -s ./solr-home```. Hit Ctlr-C on the console to stop Solr.
1. From your browser, verify that:
    1. The Solr dashboard is displayed at [Dashboard](http://localhost:8983/solr/#), using the default port 8983.
    1. The collections drop-down list contains "collection1", and that collection1 can be searched successfully using the default query parameters. Of course, at this point, the collection is empty.

### Dockerized Solr

TODO

## Solr Query Parser Plugins

"~" designates your home directory.

1. Create the directories o19s and o19s/lib under $SOLR_INSTALL/contrib
1. Copy ~/.m2/repository/com/o19s/solr/qparser/my-solr-qparser/1.0-SNAPSHOT to $SOLR_INSTALL/contrib/o19s/lib
1. Add the following line to $SOLR_HOME/collection1/conf/solrconfig.xml:
```<lib dir="${solr.install.dir:../../../..}/contrib/o19s/lib" regex=".*\.jar"/>```
1. Re-load the configuration.

 