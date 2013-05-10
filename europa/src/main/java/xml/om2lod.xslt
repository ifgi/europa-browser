<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:gml="http://www.opengis.net/gml"
	xmlns:swe="http://www.opengis.net/swe/1.0.1" 
	xmlns:om="http://www.opengis.net/om/1.0" 
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"  
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:purl="http://purl.oclc.org/NET/ssnx/ssn#" 
	xmlns:my="http://ifgi.uni-muenster.de/hydrolod#">

	<!-- 
	ONTOLOGY
	http://www.w3.org/2005/Incubator/ssn/wiki/SSN 
	http://www.w3.org/2005/Incubator/ssn/wiki/Report_Work_on_the_SSN_ontology 
	
	XML/RDF
	http://www.w3.org/TR/REC-rdf-syntax/ O&M ONTOLOGY om1:procedure Sensing 
	-->
	
	<xsl:template match="/">
		<rdf:RDF>
			<xsl:apply-templates/>
		</rdf:RDF>
	</xsl:template>
	
	
	
	<!-- IGNORE LIST --> 
	<xsl:template match="/om:ObservationCollection/gml:metaDataProperty | /om:ObservationCollection/gml:boundedBy | /om:ObservationCollection/om:member/om:Observation/*" />
	
	

	
	
	
	<!-- OBSERVATION -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation">
		<rdf:Description>
			<xsl:variable name="ObservationId" select="generate-id()" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBS_', $ObservationId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Observation" />
			<rdfs:label><xsl:copy-of select="concat('OBS_',$ObservationId)" /></rdfs:label>
			<purl:startTime>"<xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:beginPosition" />"^^xsd:dateTime</purl:startTime>
			<purl:endTime>"<xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:endPosition" />"^^xsd:dateTime</purl:endTime>
			<rdf:Description><xsl:value-of select="./gml:description" /></rdf:Description>
			<!-- links -->
			<purl:sensingMethodUsed><xsl:value-of select="concat('my:SENSING_', generate-id(om:procedure))" /></purl:sensingMethodUsed>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>
	
	<!-- SENSING -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:procedure">			
		<rdf:Description>
			<xsl:variable name="SensingId" select="generate-id()" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSING_', $SensingId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Sensing" />
			<rdfs:label><xsl:copy-of select="concat('SENSING_',$SensingId)" /></rdfs:label>
			<purl:isDescribedBy>
				<xsl:value-of select="./om:Process/gml:member/@xlink:href" />
			</purl:isDescribedBy>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>

 

	
</xsl:stylesheet>