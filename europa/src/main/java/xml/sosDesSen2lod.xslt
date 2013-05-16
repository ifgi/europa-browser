<?xml version="1.0" encoding="UTF-8"?>

<!-- 
TRANSFORMATION OF A SOS DESCRIBE SENSOR RESPONSE TO RDF TRIPLES 
 -->


<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:purl="http://purl.oclc.org/NET/ssnx/ssn#"
	xmlns:sml="http://www.opengis.net/sensorML/1.0.1">


	<xsl:template match="/">
		<rdf:RDF>
			<xsl:apply-templates/>
		</rdf:RDF>
	</xsl:template>

	
	<xsl:template match="/sml:SensorML/sml:member/sml:System">
		<xsl:variable name="SensorIdTest"><xsl:value-of select="./sml:identification/sml:IdentifierList/sml:identifier[@name='URN']/sml:Term/sml:value
															  | ./sml:identification/sml:IdentifierList/sml:identifier[@name='sensorId']/sml:Term/sml:value" /></xsl:variable>
		<xsl:variable name="SensorId">
			<xsl:choose>
				<xsl:when test="contains($SensorIdTest, 'http://')">
					<xsl:value-of select="$SensorIdTest" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat('http://ifgi.uni-muenster.de/hydrolod#', $SensorIdTest)" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
															  
		<xsl:variable name="PropertyIdTest"><xsl:value-of select="./sml:classification/sml:ClassifierList/sml:classifier[@name='phenomenon']/sml:Term/sml:value" /></xsl:variable>
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
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="$SensorId" /></xsl:attribute>
			<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#Sensor" />
			<purl:observes>
				<rdf:Description>
					<xsl:attribute name="rdf:about">
						<xsl:value-of select="$PropertyId" />
					</xsl:attribute>
				</rdf:Description>
			</purl:observes>
		</rdf:Description>
		
		<rdf:Description>
			<xsl:attribute name="rdf:about"><xsl:value-of select="$PropertyId" /></xsl:attribute>
			<rdf:type rdf:resource="http://purl.oclc.org/NET/ssnx/ssn#Property" />
		</rdf:Description>
	</xsl:template>	
	
</xsl:stylesheet>