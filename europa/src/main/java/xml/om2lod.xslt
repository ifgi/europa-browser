<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:gml="http://www.opengis.net/gml" xmlns:swe="http://www.opengis.net/swe/1.0.1" 
	xmlns:om="http://www.opengis.net/om/1.0" 
	xmlns:purl="http://purl.oclc.org/NET/ssnx/ssn#" 
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:my="http://ifgi.uni-muenster.de/hydrolod#">
	<xsl:template match="/">
		<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
			<!-- OBSERVATION -->
			<xsl:for-each select="/om:ObservationCollection/om:member/om:Observation">
				<rdf:Description>
					<xsl:variable name="ObservationId" select="generate-id()" />
					<xsl:attribute name="rdf:about">
						<xsl:value-of select="concat('my:OBS_',$ObservationId)" />
					</xsl:attribute>
					<rdf:type rdf:resource="purl:Observation" />
					<rdfs:label>
						<xsl:copy-of select="concat('OBS_',$ObservationId)" />
					</rdfs:label>
					<purl:startTime>"<xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:beginPosition"/>"^^xsd:dateTime</purl:startTime>
					<purl:endTime>"<xsl:value-of select="./om:samplingTime/gml:TimePeriod/gml:endPosition"/>"^^xsd:dateTime</purl:endTime>
					
					
					
					
					
					
					
				</rdf:Description>
			</xsl:for-each>
		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>