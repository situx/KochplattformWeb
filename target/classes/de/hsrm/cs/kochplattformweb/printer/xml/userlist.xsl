<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo" xmlns:xalan="http://xml.apache.org/xalan">
<xsl:output method="xml" version="1.0" omit-xml-declaration="yes" indent="yes"/>
<xsl:template match="userlist">
<!--<xsl:variable name="rtf"><xsl:copy-of select="$list"/></xsl:variable>-->
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
<fo:layout-master-set>
<fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="1cm" margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
<fo:region-body/>
</fo:simple-page-master>
</fo:layout-master-set>
<fo:page-sequence master-reference="simpleA4">
<fo:flow flow-name="xsl-region-body">
<fo:table>
<fo:table-column/>
<fo:table-column/>
<fo:table-column/>
<fo:table-column/>
<fo:table-body>
<fo:table-row border="solid" border-collapse="collapse">
<fo:table-cell>
<fo:block font-weight="bold"><xsl:value-of select="name"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block font-weight="bold"><xsl:value-of select="id"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block font-weight="bold"><xsl:value-of select="position"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block font-weight="bold"><xsl:value-of select="password"/></fo:block>
</fo:table-cell>
</fo:table-row>
<xsl:for-each select="list/item">
<fo:table-row border="solid" border-collapse="collapse">
<fo:table-cell>
<fo:block><xsl:value-of select="name"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="id"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="position"/></fo:block>
</fo:table-cell>
<fo:table-cell>
<fo:block><xsl:value-of select="password"/></fo:block>
</fo:table-cell>
</fo:table-row>
</xsl:for-each>
</fo:table-body>
</fo:table>
</fo:flow>
</fo:page-sequence>
</fo:root>
</xsl:template>
</xsl:stylesheet>

