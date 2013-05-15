<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:gml="http://www.opengis.net/gml"
	xmlns:om="http://www.opengis.net/om/1.0" 
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:sa="http://www.opengis.net/sampling/1.0"
	xmlns:swe="http://www.opengis.net/swe/1.0.1"
	xmlns:swe2="http://www.opengis.net/swe/2.0"
	xmlns:geo="http://www.opengis.net/def/geosparql/" 
	xmlns:foaf="http://xmlns.com/foaf/spec/#" 
	xmlns:dcterms="http://purl.org/dc/terms/" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"  
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:purl="http://purl.oclc.org/NET/ssnx/ssn#" 
	xmlns:dul="http://www.w3.org/2005/Incubator/ssn/wiki/DUL_ssn#"
	xmlns:gr="http://purl.org/goodrelations/v1#" 
	xmlns:my="http://ifgi.uni-muenster.de/hydrolod#">
	
	<xsl:template match="/">
		<rdf:RDF>
			<xsl:apply-templates/>
		</rdf:RDF>
	</xsl:template>
	
	
	<!-- IGNORE LIST -->
<xsl:template match="/om:ObservationCollection/gml:metaDataProperty 
					| /om:ObservationCollection/gml:boundedBy 
					| /om:ObservationCollection/om:member/om:Observation/gml:description
					| /om:ObservationCollection/om:member/om:Observation/om:samplingTime/gml:TimePeriod/gml:beginPosition
					| /om:ObservationCollection/om:member/om:Observation/om:samplingTime/gml:TimePeriod/gml:endPosition
					| /om:ObservationCollection/om:member/om:Observation/om:observedProperty/swe:CompositePhenomenon/*
					| /om:ObservationCollection/om:member/om:Observation/om:result/swe:DataArray/swe:elementCount/* 
					| /om:ObservationCollection/om:member/om:Observation/om:featureOfInterest/gml:FeatureCollection/gml:boundedBy/gml:Envelope/* 
					| /om:ObservationCollection/om:member/om:Observation/om:featureOfInterest/gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/gml:description 
					| /om:ObservationCollection/om:member/om:Observation/om:featureOfInterest/gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/gml:name 
					| /om:ObservationCollection/om:member/om:Observation/om:featureOfInterest/gml:FeatureCollection/gml:metaDataProperty/gml:name
					" />


	<!-- **************************************************************** -->
	<!-- OBSERVATION -->
	<!-- **************************************************************** -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation">
		<xsl:variable name="ObservationId" select="generate-id()" />
		<xsl:variable name="SensingId" select="./om:procedure/@xlink:href | 
		                                       ./om:procedure/om:Process/gml:member/@xlink:href" />
		<xsl:variable name="FoiId" select="./om:featureOfInterest/gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/@gml:id | 
								   		   ./om:featureOfInterest/gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point/gml:name" />
		<xsl:variable name="PropertyId" select="/om:ObservationCollection/om:member/om:Observation/om:result/swe:DataArray/swe:elementType/swe:DataRecord/swe:field/*/@definition |
												/om:ObservationCollection/om:member/om:Observation/om:result/swe2:DataStream/swe2:elementType/swe2:DataRecord/swe2:field/*/@definition" />
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Observation" />
			<rdfs:label><xsl:copy-of select="concat('OBSERVATION_',$ObservationId)" /></rdfs:label>
			<purl:startTime rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime"><xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:beginPosition"/></purl:startTime>
			<purl:endTime rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime"><xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:endPosition"/></purl:endTime>
			<dcterms:description><xsl:value-of select="./gml:description" /></dcterms:description>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>
	
	<!-- **************************************************************** -->
	<!-- SENSING -->
	<!-- **************************************************************** -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:procedure">
		<xsl:variable name="SensingId" select="./@xlink:href | ./om:Process/gml:member/@xlink:href" />
		<!-- Incoming relations -->
		<rdf:Description>
			<xsl:variable name="ObservationId" select="generate-id(..)" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
			<purl:sensingMethodUsed>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
				</rdf:Description>
			</purl:sensingMethodUsed>
		</rdf:Description>
		<!-- sensing -->
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
			<rdf:type rdf:resource="purl:Sensing" />
		</rdf:Description>
	</xsl:template>
	
	<!-- **************************************************************** -->
	<!-- PROPERTY (swe:field) -->
	<!-- **************************************************************** -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:result/swe:DataArray/swe:elementType/swe:DataRecord/swe:field | 
						 /om:ObservationCollection/om:member/om:Observation/om:result/swe2:DataStream/swe2:elementType/swe2:DataRecord/swe2:field">
		<xsl:if test="not(descendant::swe2:value)"><!-- Skips fields including values within themselves -->
			<xsl:variable name="PropertyId" select="./*/@definition" />
			<xsl:variable name="ObservationId" select="generate-id(../../../../..)" />
			<!-- Incoming relations -->
			<rdf:Description>
				<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
				<purl:observedProperty>
					<rdf:Description>
						<xsl:attribute name="rdf:about"><xsl:value-of select="$PropertyId" /></xsl:attribute>
					</rdf:Description>
				</purl:observedProperty>
			</rdf:Description>
			<!-- property -->
			<rdf:Description>
				<xsl:attribute name="rdf:about"><xsl:value-of select="$PropertyId" /></xsl:attribute>
				<rdf:type rdf:resource="purl:Property" />
				<rdfs:label><xsl:copy-of select="./@name" /></rdfs:label>
				<gr:hasUnitOfMeasurement>
					<xsl:if test="./*/swe:uom/@code | ./*/swe2:uom/@code">
						<xsl:value-of select="./*/swe:uom/@code | ./*/swe2:uom/@code" />
					</xsl:if>
					<xsl:if test="./*/swe:uom/@xlink:href | ./*/swe2:uom/@xlink:href">
						<rdf:Description>
							<xsl:attribute name="rdf:about">
								<xsl:value-of select="./*/swe:uom/@xlink:href | ./*/swe2:uom/@xlink:href" />
							</xsl:attribute>
						</rdf:Description>
					</xsl:if>
				</gr:hasUnitOfMeasurement>
				<!-- links -->
				<purl:isPropertyOf>
					<rdf:Description>
						<xsl:variable name="FoiId" select="../../../../../om:featureOfInterest/gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/@gml:id | 
												   		   ../../../../../om:featureOfInterest/gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point/gml:name" />
						<xsl:attribute name="rdf:about"><xsl:value-of select="$FoiId" /></xsl:attribute>
					</rdf:Description>
				</purl:isPropertyOf>				
			</rdf:Description>
		</xsl:if>
 	</xsl:template>			
	
	
	<!-- **************************************************************** -->
	<!-- FEATURE OF INTEREST -->
	<!-- **************************************************************** -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:featureOfInterest">
		<xsl:variable name="FoiId" select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/@gml:id | 
								   		   ./gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point/gml:name" />
		<!-- Incoming relations -->
		<rdf:Description>
			<xsl:variable name="ObservationId" select="generate-id(..)" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
			<purl:featureOfInterest>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="$FoiId" /></xsl:attribute>
				</rdf:Description>
			</purl:featureOfInterest>
		</rdf:Description>	
		<!-- Feature of interest -->
		<rdf:Description>
			<xsl:variable name="FoiName" select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/gml:name/text()" />									   		   
			<xsl:attribute name="rdf:about"><xsl:value-of select="$FoiId" /></xsl:attribute>
			<rdf:type rdf:resource="purl:FeatureOfInterest" />
			<!-- links -->
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
			<dcterms:description><xsl:value-of select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/gml:description" /></dcterms:description>
		</rdf:Description>
		<xsl:apply-templates/>
	</xsl:template>
	
	
	<!-- **************************************************************** -->
	<!-- POINT -->
	<!-- **************************************************************** -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:featureOfInterest/gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/sa:position/gml:Point | 
						 /om:ObservationCollection/om:member/om:Observation/om:featureOfInterest/gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point">
		<xsl:variable name="PointId" select="generate-id()" />
		<!-- Incoming relations -->
		<rdf:Description>
		<xsl:variable name="FoiId" select="../../@gml:id | 
								   		   ./gml:name" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="$FoiId" /></xsl:attribute>
			<geo:defaultGeometry>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:POINT_', $PointId)" /></xsl:attribute>
				</rdf:Description>
			</geo:defaultGeometry>
		</rdf:Description>
		<!-- Point -->
		<rdf:Description>
			<xsl:variable name="SrsId" select="./gml:pos/@srsName |
											   ../../@srsName" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:POINT_', $PointId)" /></xsl:attribute>
			<rdf:type rdf:resource="geo:Point" />
			<rdfs:label><xsl:copy-of select="concat('POINT_', $PointId)" /></rdfs:label>
			<geo:asWKT rdf:datatype="http://www.opengis.net/def/geosparql/wktLiteral">
				<xsl:variable name="Coords" select="./gml:pos/text()" />
				<xsl:variable name="Wkt" select="concat('&lt;', $SrsId, '&gt;', ' POINT(', $Coords, ')')" />
				<xsl:value-of select="$Wkt" disable-output-escaping="no" />
			</geo:asWKT>
		</rdf:Description>
	</xsl:template>
	
	
	<!-- **************************************************************** -->
	<!-- SENSOR OUTPUT each sensor output is one row of the value array -->
	<!-- **************************************************************** --> 
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:result/swe:DataArray/swe:values
  						 | /om:ObservationCollection/om:member/om:Observation/om:result/swe2:DataStream/swe2:values">

		<xsl:variable name="decimalSeparator" select="../swe:encoding/swe:TextBlock/@decimalSeparator
													| ../swe2:encoding/swe2:TextEncoding/@decimalSeparator" />
		<xsl:variable name="tokenSeparator" select="../swe:encoding/swe:TextBlock/@tokenSeparator
												  | ../swe2:encoding/swe2:TextEncoding/@tokenSeparator" />
		<xsl:variable name="blockSeparator" select="../swe:encoding/swe:TextBlock/@blockSeparator
												  | ../swe2:encoding/swe2:TextEncoding/@blockSeparator" />
												  
		<xsl:variable name="ObservationId" select="generate-id(../../..)" />
		<xsl:call-template name="processDataArray">	
			<xsl:with-param name="dataArray" select="." />
			<xsl:with-param name="rowSep" select="$blockSeparator" /> 
			<xsl:with-param name="tokenSep" select="$tokenSeparator" />
			<xsl:with-param name="ObservationId" select="$ObservationId" />
			<xsl:with-param name="daCount" select="1" />
		</xsl:call-template>
	</xsl:template>
	
	<!-- PARSING DATA ARRAY -->
	<xsl:template name="processDataArray">
		<xsl:param name="dataArray" /><!-- text to process -->
		<xsl:param name="rowSep" /><!-- Row separator -->
		<xsl:param name="tokenSep" /><!-- Token separator -->
		<xsl:param name="ObservationId" />
		<xsl:param name="daCount" />
		
		<xsl:param name="normDataArray" select="normalize-space(string($dataArray))" />



<!-- All rows except the 1st -->
<!-- EL PROBLEMA SE DA AL USAR ENTER COMO SEPARADOR DE ROWS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
<xsl:param name="remainingDataArray" select="substring-after($normDataArray, $rowSep)" />






		<xsl:param name="row"><!-- Current row -->
			<xsl:choose>
				<xsl:when test="contains($normDataArray, $rowSep)">
					<xsl:value-of select="substring-before($normDataArray, $rowSep)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$normDataArray" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:param> 
			
			













		
		<xsl:choose>
			<xsl:when test="string-length(string($normDataArray)) &gt; 0"><!-- Termination condition -->
				<!-- Operation code -->
<!-- ****************************************************************************************************** -->
<xsl:variable name="SensorOutputId" select="$daCount" /><!-- XSLT RANDOM NUMBERS??????????????????????????????????????????????? -->
<!-- ****************************************************************************************************** -->
				<rdf:Description>
 					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBSERVATION_', $ObservationId)" /></xsl:attribute>
					<purl:observationResult>
						<rdf:Description>
							<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>							
						</rdf:Description>
					</purl:observationResult>
				</rdf:Description>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>
					<rdf:type rdf:resource="purl:SensorOutput" />
				</rdf:Description>
				<!-- Goes for the observed values --> 
				<xsl:call-template name="processDataRow">
					<xsl:with-param name="dataRow" select="$row" />
					<xsl:with-param name="tokenSep" select="$tokenSep" />
					<xsl:with-param name="ObservationId" select="$ObservationId" />
					<xsl:with-param name="SensorOutputId" select="$SensorOutputId" />
					<xsl:with-param name="drCount" select="1" />
				</xsl:call-template>
				<!-- Recursive call -->
				<xsl:call-template name="processDataArray">
					<xsl:with-param name="dataArray" select="$remainingDataArray" />
					<xsl:with-param name="rowSep" select="$rowSep" />
					<xsl:with-param name="tokenSep" select="$tokenSep" />
					<xsl:with-param name="ObservationId" select="$ObservationId" />
					<xsl:with-param name="daCount" select="$daCount + 1" />
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	
	<!-- PARSING ROW -->
	<xsl:template name="processDataRow">
		<xsl:param name="dataRow" /><!-- text to process -->
		<xsl:param name="tokenSep" /><!-- Token separator -->
		<xsl:param name="ObservationId" />
		<xsl:param name="SensorOutputId" />
		<xsl:param name="drCount" />
		<xsl:param name="normDataRow" select="normalize-space(string($dataRow))" />
		<xsl:param name="remainingDataRow" select="substring-after($normDataRow, $tokenSep)" /><!-- All tokens except the 1st -->
		<xsl:param name="token"><!-- Current token -->
			<xsl:choose>
				<xsl:when test="contains($normDataRow, $tokenSep)">
					<xsl:value-of select="substring-before($normDataRow, $tokenSep)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$normDataRow" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:param>
		
		<xsl:choose>
			<xsl:when test="string-length(string($normDataRow)) &gt; 0"><!-- Termination condition -->
				<!-- Operation code -->
<!-- ****************************************************************************************************** -->			
<xsl:variable name="ObservedValueId" select="$drCount" /><!-- XSLT RANDOM NUMBERS??????????????????????????????????????????????? -->
<!-- ****************************************************************************************************** -->
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>
					<purl:hasValue>
						<rdf:Description>
							<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBS_VALUE_', $ObservationId, '_', $SensorOutputId, '_', $ObservedValueId)" /></xsl:attribute>
						</rdf:Description>
					</purl:hasValue>
				</rdf:Description>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('my:OBS_VALUE_', $ObservationId, '_', $SensorOutputId, '_', $ObservedValueId)" /></xsl:attribute>
					<rdf:type rdf:resource="purl:ObservationValue" />
					<purl:hasValue>
						<xsl:value-of select="$token" />
					</purl:hasValue>
				</rdf:Description>
				<!-- Recursive call -->
				<xsl:call-template name="processDataRow">
					<xsl:with-param name="dataRow" select="$remainingDataRow" />
					<xsl:with-param name="tokenSep" select="$tokenSep" />
					<xsl:with-param name="ObservationId" select="$ObservationId" />
					<xsl:with-param name="SensorOutputId" select="$SensorOutputId" />
					<xsl:with-param name="drCount" select="$drCount + 1" />
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>	
	
</xsl:stylesheet>