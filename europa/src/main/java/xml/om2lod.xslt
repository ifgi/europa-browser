<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:om="http://www.opengis.net/om/1.0" 
	xmlns:purl="http://purl.oclc.org/NET/ssnx/ssn#" 
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:my="http://ifgi.uni-muenster.de/hydrolod#" 
	>
	<xsl:template match="/">

		<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
			<xsl:for-each select="/om:ObservationCollection/om:member/om:Observation">
				<rdf:Description><!-- rdf:about="{concat('my:OBS_',generate-id())}" -->
					<xsl:variable name="ObservationId" select="generate-id()" />
					<xsl:attribute name="rdf:about">
						<xsl:value-of select="concat('my:OBS_',$ObservationId)" />
					</xsl:attribute>
					<rdf:type rdf:resource="purl:Observation"/>
					
					<rdfs:label><xsl:copy-of select="concat('OBS_',$ObservationId)" /></rdfs:label>	
				</rdf:Description>
			</xsl:for-each>
		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>