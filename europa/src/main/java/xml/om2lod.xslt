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
	xmlns:dul="http://www.w3.org/2005/Incubator/ssn/wiki/DUL_ssn#" 
	xmlns:my="http://ifgi.uni-muenster.de/hydrolod#"
	>

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
	<xsl:template match="/om:ObservationCollection/gml:metaDataProperty | 
						/om:ObservationCollection/gml:boundedBy | 
						/om:ObservationCollection/om:member/om:Observation/* | 
						/om:ObservationCollection/om:member/om:Observation/om:observedProperty/*
						" />
	
	<!-- OBSERVATION (om:Observation) -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation">
		<rdf:Description>
			<xsl:variable name="ObservationId" select="generate-id()" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Observation" />
			<rdfs:label><xsl:copy-of select="concat('OBSERVATION_',$ObservationId)" /></rdfs:label>
			<purl:startTime rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime"><xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:beginPosition"/></purl:startTime>
			<purl:endTime rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime"><xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:endPosition"/></purl:endTime>
			<rdf:Description><xsl:value-of select="./gml:description" /></rdf:Description>
			<!-- links 
<purl:featureOfInterest></purl:featureOfInterest>
<purl:observedProperty></purl:observedProperty>
<purl:observedBy></purl:observedBy>
<purl:observationResult></purl:observationResult>
			-->
			<purl:sensingMethodUsed><xsl:value-of select="concat('my:SENSING_', generate-id(om:procedure))" /></purl:sensingMethodUsed>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>
	
	
	<!-- SENSING (om:procedure) -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:procedure">			
		<rdf:Description>
			<xsl:variable name="SensingId" select="generate-id()" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSING_', $SensingId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Sensing" />
			<rdfs:label><xsl:copy-of select="concat('SENSING_',$SensingId)" /></rdfs:label>
			<dul:isDescribedBy>
				<xsl:value-of select="./om:Process/gml:member/@xlink:href" />
			</dul:isDescribedBy>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>


	<!-- PROPERTY (om:observedProperty) -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:observedProperty">
		<rdf:Description>
			<xsl:variable name="PropertyId" select="generate-id()" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:PROPERTY_', $PropertyId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Property" />
			<rdfs:label><xsl:copy-of select="concat('PROPERTY_',$PropertyId)" /></rdfs:label>
			<!-- links
<purl:isPropertyOf></purl:isPropertyOf>
 			-->
			<purl:isDescribedBy><rdf:Description rdf:about="{./swe:CompositePhenomenon/swe:component/@xlink:href}"></rdf:Description></purl:isDescribedBy>
			
			
			


		</rdf:Description>
		<xsl:apply-templates/>
 	</xsl:template>
 
 

	
</xsl:stylesheet>