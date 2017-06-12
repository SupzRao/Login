package com.cidaassdk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Suprada on 12-Apr-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomFieldDataEntity{

        private static final long serialVersionUID = 1L;
      //  private CustomFiledType dataType;
        private String value;
        private boolean internal;
        private boolean readOnly;

        public static long getSerialVersionUID() {
                return serialVersionUID;
        }

        public String getValue() {
                return value;
        }

        public void setValue(String value) {
                this.value = value;
        }

        public boolean isInternal() {
                return internal;
        }

        public void setInternal(boolean internal) {
                this.internal = internal;
        }

        public boolean isReadOnly() {
                return readOnly;
        }

        public void setReadOnly(boolean readOnly) {
                this.readOnly = readOnly;
        }
}
