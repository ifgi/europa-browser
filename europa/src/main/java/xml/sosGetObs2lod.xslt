<?xml version="1.0" encoding="UTF-8"?>
<!--
   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
 -->


<!-- 
TRANSFORMATION OF A SOS GET OBSERVATION RESPONSE TO RDF TRIPLES 
 -->

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
	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
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
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBSERVATION_', $ObservationId)" /></xsl:attribute>
				<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#Observation" />
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
		<xsl:variable name="SensingIdTest" select="./@xlink:href | ./om:Process/gml:member/@xlink:href" />
		<xsl:variable name="SensingId">
				<xsl:choose>
				<xsl:when test="contains($SensingIdTest, 'http://')">
					<xsl:value-of select="$SensingIdTest" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $SensingIdTest)" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>		
		<!-- Incoming relations -->
		<rdf:Description>
			<xsl:variable name="ObservationId" select="generate-id(..)" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBSERVATION_', $ObservationId)" /></xsl:attribute>
			<purl:sensingMethodUsed>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
				</rdf:Description>
			</purl:sensingMethodUsed>
		</rdf:Description>
		<!-- sensing -->
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="$SensingId" /></xsl:attribute>
			<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#Sensing" />
		</rdf:Description>
	</xsl:template>
	
	<!-- **************************************************************** -->
	<!-- PROPERTY (swe:field) -->
	<!-- **************************************************************** -->
	<xsl:template match="/om:ObservationCollection/om:member/om:Observation/om:result/swe:DataArray/swe:elementType/swe:DataRecord/swe:field | 
						 /om:ObservationCollection/om:member/om:Observation/om:result/swe2:DataStream/swe2:elementType/swe2:DataRecord/swe2:field">
		<xsl:if test="not(descendant::swe2:value)"><!-- Skips fields including values within themselves -->
			<xsl:variable name="PropertyIdTest"><xsl:value-of select="./*/@definition" /></xsl:variable>
			<xsl:variable name="PropertyId">
				<xsl:choose>
					<xsl:when test="contains($PropertyIdTest, 'http://')">
						<xsl:value-of select="$PropertyIdTest" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $PropertyIdTest)" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="ObservationId" select="generate-id(../../../../..)" />
			<!-- Incoming relations -->
			<rdf:Description>
				<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBSERVATION_', $ObservationId)" /></xsl:attribute>
				<purl:observedProperty>
					<rdf:Description>
						<xsl:attribute name="rdf:about"><xsl:value-of select="$PropertyId" /></xsl:attribute>
					</rdf:Description>
				</purl:observedProperty>
			</rdf:Description>
			<!-- property -->
			<rdf:Description>
				<xsl:attribute name="rdf:about"><xsl:value-of select="$PropertyId" /></xsl:attribute>
				<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#Property" />
				<rdfs:label><xsl:value-of select="./@name" /></rdfs:label>
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
				<xsl:if test="./swe:Time" >
					<purl:hasQuality>
						<xsl:variable name="qualityTime" select="'http://dbpedia.org/resource/Time'" />
						<rdf:Description>
							<xsl:attribute name="rdf:about">
								 <xsl:value-of select="$qualityTime" />
							</xsl:attribute>
						</rdf:Description>
					</purl:hasQuality>
				</xsl:if>
				<xsl:if test="./swe:Text">
					<purl:hasQuality>
						<xsl:variable name="qualityText" select="'http://dbpedia.org/page/Plain_text'" />
						<rdf:Description>
							<xsl:attribute name="rdf:about">
								<xsl:value-of select="$qualityText" />
							</xsl:attribute>
						</rdf:Description>
					</purl:hasQuality>
				</xsl:if>				
				<xsl:if test="./swe:Quantity">
					<purl:hasQuality>
						<xsl:variable name="qualityQuantity" select="'http://dbpedia.org/page/Quantity'" />
						<rdf:Description>
							<xsl:attribute name="rdf:about">
								<xsl:value-of select="$qualityQuantity" />
							</xsl:attribute>
						</rdf:Description>
					</purl:hasQuality>
				</xsl:if>
				<!-- links -->
				<purl:isPropertyOf>
					<rdf:Description>
						<xsl:variable name="FoiIdTest" select="../../../../../om:featureOfInterest/gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/@gml:id | 
												   			   ../../../../../om:featureOfInterest/gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point/gml:name" />
						<xsl:variable name="FoiId" >						   		   
							<xsl:choose>
								<xsl:when test="contains($FoiIdTest, 'http://')">
									<xsl:value-of select="$FoiIdTest" />
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $FoiIdTest)" />
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>			   		   
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
		<xsl:variable name="FoiIdTest" select="./gml:FeatureCollection/gml:featureMember/sa:SamplingPoint/@gml:id | 
								   		   ./gml:FeatureCollection/gml:location/gml:MultiPoint/gml:pointMembers/gml:Point/gml:name" />
		<xsl:variable name="FoiId" >						   		   
			<xsl:choose>
				<xsl:when test="contains($FoiIdTest, 'http://')">
					<xsl:value-of select="$FoiIdTest" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $FoiIdTest)" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- Incoming relations -->
		<rdf:Description>
			<xsl:variable name="ObservationId" select="generate-id(..)" />
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBSERVATION_', $ObservationId)" /></xsl:attribute>
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
			<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#FeatureOfInterest" />
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
		<xsl:variable name="SrsId" select="./gml:pos/@srsName | ../../@srsName" />
		<xsl:variable name="Coords" select="./gml:pos/text()" />
		<xsl:variable name="PointId" select="translate(concat($SrsId, '_', $Coords), ' ', '_')" />
		<!-- Incoming relations -->
		<rdf:Description>
			<xsl:variable name="FoiIdTest" select="../../@gml:id | 
									   		   ./gml:name" />
			<xsl:variable name="FoiId">
				<xsl:choose>
					<xsl:when test="contains($FoiIdTest, 'http://')">
						<xsl:value-of select="$FoiIdTest" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $FoiIdTest)" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable> 		   
			<xsl:attribute name="rdf:about"><xsl:value-of select="$FoiId" /></xsl:attribute>
			<geo:defaultGeometry>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'POINT_', $PointId)" /></xsl:attribute>
				</rdf:Description>
			</geo:defaultGeometry>
		</rdf:Description>
		<!-- Point -->
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'POINT_', $PointId)" /></xsl:attribute>
			<rdf:type rdf:resource="geo:Point" />
			<rdfs:label><xsl:copy-of select="concat('POINT_', $PointId)" /></rdfs:label>
			<geo:asWKT rdf:datatype="http://www.opengis.net/def/geosparql/wktLiteral">
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
	
	
	<!-- **************************************************************** -->
	<!-- PARSING DATA ARRAY -->
	<!-- **************************************************************** -->
	<xsl:template name="processDataArray">
		<xsl:param name="dataArray" /><!-- text to process -->
		<xsl:param name="rowSep" /><!-- Row separator -->
		<xsl:param name="tokenSep" /><!-- Token separator -->
		<xsl:param name="ObservationId" />
		<xsl:param name="daCount" />

		<xsl:variable name="afterLeadingWS" select="substring-after($dataArray, substring-before($dataArray,substring-before(normalize-space($dataArray), ' ')))"/>		
		<xsl:variable name="SensorOutputId" select="$daCount" />
		
		 <xsl:choose>
		 	<xsl:when test="contains($afterLeadingWS, $rowSep)"><!-- <xsl:when test="contains($afterLeadingWS, '&#xA;')"> -->
				<rdf:Description>
						<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBSERVATION_', $ObservationId)" /></xsl:attribute>
					<purl:observationResult>
						<rdf:Description>
							<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>							
						</rdf:Description>
					</purl:observationResult>
				</rdf:Description>
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>		
					<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#SensorOutput" />
				</rdf:Description>
		 		<!-- OBTIENE UN ROW -->
				<xsl:call-template name="processDataRow">
					<xsl:with-param name="dataRow" select="substring-before($afterLeadingWS, $rowSep)"/><!-- <xsl:with-param name="dataRow" select="substring-before($afterLeadingWS, '&#xA;')"/> -->
					<xsl:with-param name="tokenSep" select="$tokenSep"/>
					<xsl:with-param name="ObservationId" select="$ObservationId"/>
					<xsl:with-param name="SensorOutputId" select="$SensorOutputId"/>
					<xsl:with-param name="drCount" select="1"/>
				 </xsl:call-template>
		 		<!-- RECURSIVE CALL -->
		 		<xsl:call-template name="processDataArray">
		 			 <xsl:with-param name="dataArray" select="substring-after($afterLeadingWS, $rowSep)"/><!-- <xsl:with-param name="dataArray" select="substring-after($afterLeadingWS, '&#xA;')"/> -->
		 			 <xsl:with-param name="rowSep" select="$rowSep"/>
		 			 <xsl:with-param name="tokenSep" select="$tokenSep"/>
		 			 <xsl:with-param name="ObservationId" select="$ObservationId"/>
		 			 <xsl:with-param name="daCount" select="$daCount + 1"/>
		 		</xsl:call-template>
		 	</xsl:when>
		 	<!-- 
		 	<xsl:otherwise>
		 	</xsl:otherwise>
		 	 -->
		 </xsl:choose>
	</xsl:template>

	
	<!-- **************************************************************** -->
	<!-- PARSING ROW -->
	<!-- **************************************************************** -->
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
				<xsl:variable name="ObservedValueId" select="$drCount" />
				<rdf:Description>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#' , 'SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>
					<purl:hasValue>
						<rdf:Description>
							<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBS_VALUE_', $ObservationId, '_', $SensorOutputId, '_', $ObservedValueId)" /></xsl:attribute>
						</rdf:Description>
					</purl:hasValue>
				</rdf:Description>
<!-- *************************************************************************************** -->
<!-- HACK: This assumes the sampling time is ALWAYS the first property, this is true for 52N SOS but... -->
<!-- Adds the sampling time as a property for the SENSOR_OUTPUT. This is duplicated since ST is also a OBS_VALUE -->
				<xsl:if test="$ObservedValueId  = '1'">
					<rdf:Description>
						<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#' , 'SENSOR_OUTPUT_', $ObservationId, '_', $SensorOutputId)" /></xsl:attribute>
						<purl:observationSamplingTime>
				<xsl:value-of select="$token" />
						</purl:observationSamplingTime>
					</rdf:Description>
				</xsl:if> 
<!-- *************************************************************************************** -->
				<rdf:Description>
					<xsl:variable name="PropertyIdTest"><xsl:value-of select="../swe:elementType/swe:DataRecord/swe:field[not (descendant::swe2:value)][$drCount]/*/@definition
				  														     | ../swe2:elementType/swe2:DataRecord/swe2:field[not (descendant::swe2:value)][$drCount]/*/@definition" /></xsl:variable>
					<xsl:variable name="PropertyId">
						<xsl:choose>
							<xsl:when test="contains($PropertyIdTest, 'http://')">
								<xsl:value-of select="$PropertyIdTest" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $PropertyIdTest)" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:attribute name="rdf:about"><xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', 'OBS_VALUE_', $ObservationId, '_', $SensorOutputId, '_', $ObservedValueId)" /></xsl:attribute>
					<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#ObservationValue" />
					<purl:forProperty>
						<rdf:Description>
							<xsl:attribute name="rdf:about"><xsl:value-of select="$PropertyId" /></xsl:attribute>
						</rdf:Description>
					</purl:forProperty>
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