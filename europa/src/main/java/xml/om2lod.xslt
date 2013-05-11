<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:gml="http://www.opengis.net/gml"
	xmlns:swe="http://www.opengis.net/swe/1.0.1" 
	xmlns:om="http://www.opengis.net/om/1.0" 
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:sa="http://www.opengis.net/sampling/1.0"
	xmlns:foaf="http://xmlns.com/foaf/spec/#" 
	xmlns:dcterms="http://purl.org/dc/terms/" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"  
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:purl="http://purl.oclc.org/NET/ssnx/ssn#" 
	xmlns:dul="http://www.w3.org/2005/Incubator/ssn/wiki/DUL_ssn#"
	xmlns:my="http://ifgi.uni-muenster.de/hydrolod#"
	>

	<!-- 
	ONTOLOGY
	http://www.w3.org/2005/Incubator/ssn/wiki/Report_Work_on_the_SSN_ontology 
	http://www.w3.org/2005/Incubator/ssn/wiki/SSN 
	http://www.w3.org/2005/Incubator/ssn/ssnx/ssn
	
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
						/om:ObservationCollection/om:member/om:Observation/om:observedProperty/swe:CompositePhenomenon/*
						" />
	
	
	<!-- OBSERVATION (om:Observation) -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation">
		<xsl:variable name="ObservationId" select="generate-id()" />
		<xsl:variable name="SensingId" select="./om:procedure/@xlink:href | 
		                                       ./om:procedure/om:Process/gml:member/@xlink:href" />
		<xsl:variable name="PropertyId" select="generate-id(./om:observedProperty)" />
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Observation" />
			<rdfs:label><xsl:copy-of select="concat('OBSERVATION_',$ObservationId)" /></rdfs:label>
			<purl:startTime rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime"><xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:beginPosition"/></purl:startTime>
			<purl:endTime rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime"><xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:endPosition"/></purl:endTime>
			<dcterms:description><xsl:value-of select="./gml:description" /></dcterms:description>
			
			<!-- links 
<purl:featureOfInterest></purl:featureOfInterest> 
<purl:observationResult></purl:observationResult> SENSOROUTPUT
			-->
			<purl:observedBy>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSOR_', $ObservationId)" /></xsl:attribute><!-- Sensor data not available in SOS GetObservation request -->				
				</rdf:Description>	
			</purl:observedBy>
			<purl:sensingMethodUsed>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
				</rdf:Description>	
			</purl:sensingMethodUsed>
			<purl:observedProperty>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:PROPERTY_', $PropertyId)" /></xsl:attribute>
				</rdf:Description>
			</purl:observedProperty>
		</rdf:Description>
		<!-- SENSOR 
			 It needs a SOS DescribeSensor request to retrieve data
			 LOD discourages blank nodes, so the sensor has the same ID as the observation -->
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSOR_', $ObservationId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Sensor" />
			<rdfs:label><xsl:copy-of select="concat('SENSOR_',$ObservationId)" /></rdfs:label>
			<!-- links -->
			<purl:detects>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="./om:observedProperty/swe:CompositePhenomenon/swe:component/@xlink:href" /></xsl:attribute>					
				</rdf:Description>
			</purl:detects>
			<purl:observes>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:PROPERTY_', $PropertyId)" /></xsl:attribute>
				</rdf:Description>
			</purl:observes>
			<purl:implements>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
				</rdf:Description>
			</purl:implements>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>
	
	
	<!-- SENSING (om:procedure) -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:procedure">			
		<rdf:Description>
			<xsl:variable name="SensingId" select="./@xlink:href | ./om:Process/gml:member/@xlink:href" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Sensing" />
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
<purl:isPropertyOf></purl:isPropertyOf> FOI
			-->
		</rdf:Description>
		<xsl:apply-templates/>
 	</xsl:template>	
	

	<!-- STIMULUS (swe:CompositePhenomenon/swe:component) -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:observedProperty/swe:CompositePhenomenon/swe:component">
		<rdf:Description>
			<xsl:variable name="StimulusId" select="./@xlink:href" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="$StimulusId" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Stimulus" />
			<!-- links --> 
			<purl:isProxyFor>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:PROPERTY_', generate-id(../..))" /></xsl:attribute>				
				</rdf:Description>
			</purl:isProxyFor>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>

	
	<!-- FEATURE OF INTEREST -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:featureOfInterest">
		<rdf:Description>
			<xsl:variable name="FoiId" select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/@gml:id | 
									   		   ./gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point/gml:name" />
			<xsl:variable name="FoiName" select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/gml:name/text()" />									   		   
			<xsl:attribute name="rdf:about"><xsl:value-of select="$FoiId" /></xsl:attribute>
			<rdf:type rdf:resource="purl:FeatureOfInterest" />
			<xsl:choose>
				<xsl:when test="$FoiName != ''">
					<foaf:name><xsl:copy-of select="$FoiName" /></foaf:name>
					<rdfs:label><xsl:copy-of select="$FoiName" /></rdfs:label>
				</xsl:when>
				<xsl:otherwise>
					<foaf:name><xsl:value-of select="$FoiId" /></foaf:name>
					<rdfs:label><xsl:value-of select="$FoiId" /></rdfs:label>
				</xsl:otherwise>
			</xsl:choose>
			<dcterms:description>
				<xsl:value-of select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/gml:description" />
			</dcterms:description>

				
			
			
			
			

			
			
		</rdf:Description>
	</xsl:template>
	
	
	
	<!-- SENSOR OUTPUT -->
	
	
	

 			
	
	
	
	
	
	
	
	
	
</xsl:stylesheet>