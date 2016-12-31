<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo" xmlns:xalan="http://xml.apache.org/xalan">
<xsl:output method="xml" version="1.0" omit-xml-declaration="yes" indent="yes"/>
<xsl:param name="amount"/> 
<xsl:param name="personamount"/> 
<xsl:param name="name"/> 
<xsl:param name="fat"/> 
<xsl:param name="protein"/> 
<xsl:param name="carbohydrates"/> 
<xsl:param name="calories"/> 
<xsl:param name="difficult"/> 
<xsl:param name="preparation"/> 
<xsl:param name="time"/> 
<xsl:param name="category"/> 
<xsl:param name="list"/>
<xsl:param name="picture"/> 
<xsl:template match="recipe">
<!--<xsl:variable name="rtf"><xsl:copy-of select="$list"/></xsl:variable>-->
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
<fo:layout-master-set>
<fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="1.5cm" margin-right="1.5cm">
<fo:region-body/>
</fo:simple-page-master>
</fo:layout-master-set>
<fo:page-sequence master-reference="simpleA4">
<fo:flow flow-name="xsl-region-body">
<fo:block font-size="24pt" text-align="center" font-weight="bold" space-after="10mm">
<xsl:value-of select="stringdesc"/><xsl:value-of select="$name"/>
</fo:block>
<fo:block font-weight="bold"><xsl:value-of select="recipe"/></fo:block>
<fo:table inline-progression-dimension="13cm" table-layout="fixed" space-after="0.2cm">
<fo:table-column column-width="5cm"/>
<fo:table-column column-width="4cm"/>
<fo:table-body>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="name"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$name"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="personamount"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$personamount"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="difficult"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$difficult"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="category"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$category"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="time"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$time"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="amount"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$amount"/></fo:block>
</fo:table-cell>
</fo:table-row>
</fo:table-body>
</fo:table>
<fo:table inline-progression-dimension="auto" table-layout="auto" space-after="0.2cm">
<fo:table-column column-width="5cm"/>
<fo:table-column column-width="4cm"/>
<fo:table-body>
<fo:table-row>
<fo:table-cell>
<fo:block font-weight="bold"><xsl:value-of select="intgrediant"/></fo:block>
</fo:table-cell>
</fo:table-row>
<xsl:for-each select="list/item">
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="name"/>:</fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="set"/></fo:block>
</fo:table-cell>
</fo:table-row>
</xsl:for-each>
</fo:table-body>
</fo:table>
<fo:block-container position="absolute" top="25mm" right="2mm">
<fo:block text-align="right">
<fo:external-graphic src="url('{$picture}')" text-align="right"/>
</fo:block>
</fo:block-container>
<fo:table inline-progression-dimension="auto" table-layout="auto" space-after="1cm">
<fo:table-column column-width="5cm"/>
<fo:table-column column-width="4cm"/>
<fo:table-body>
<fo:table-row>
<fo:table-cell>
<fo:block font-weight="bold"><xsl:value-of select="nutrition"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block> </fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="fat"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$fat"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="calories"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$calories"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="carbohydrates"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$carbohydrates"/></fo:block>
</fo:table-cell>
</fo:table-row>
<fo:table-row>
<fo:table-cell>
<fo:block><xsl:value-of select="protein"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="$protein"/></fo:block>
</fo:table-cell>
</fo:table-row>
</fo:table-body>
</fo:table>
<fo:block font-size="12pt" text-align="left" font-weight="bold" space-after="2mm">
<xsl:value-of select="praparation"/>
</fo:block>
<fo:block font-size="12pt" text-align="left" space-after="15mm">
<xsl:value-of select="$preparation"/>
</fo:block>
</fo:flow>
</fo:page-sequence>
</fo:root>
</xsl:template>
</xsl:stylesheet>

