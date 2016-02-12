package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.documentstore.client.DocumentStoreClient;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;

public class DocStoreServiceProxyImpl implements DocStoreServiceProxy {

    
    public GetDocResponse getDocument(long docStoreId)throws MitchellException{
        
        GetDocResponse docResponse = null;
        
        docResponse = DocumentStoreClient.getDocument(docStoreId);
                
        return docResponse;
    }
}
