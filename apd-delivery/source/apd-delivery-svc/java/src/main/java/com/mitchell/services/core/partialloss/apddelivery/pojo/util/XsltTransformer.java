package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import com.mitchell.common.exception.MitchellException;

/**
 * The Interface XsltTransformer.
 */
public interface XsltTransformer {

    /**
     * Transform xml string.
     *
     * @param styleSheetFileName the style sheet file name
     * @param xmlString the xml string
     * @return the string
     * @throws MitchellException the mitchell exception
     */
    String transformXmlString(
                        String styleSheetFileName,
                        String xmlString) 
                        throws MitchellException;
}
