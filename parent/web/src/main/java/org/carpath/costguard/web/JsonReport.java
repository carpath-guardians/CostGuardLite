package org.carpath.costguard.web;

public class JsonReport {
    private String tenant;

    public String getTenant() {
        return tenant;
    }

    public JsonReport setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public static class JsonReportItem {
        private String description;
        private int thresholdDelta;

        public String getDescription() {
            return description;
        }

        public JsonReportItem setDescription(String description) {
            this.description = description;
            return this;
        }

        public int getThresholdDelta() {
            return thresholdDelta;
        }

        public JsonReportItem setThresholdDelta(int thresholdDelta) {
            this.thresholdDelta = thresholdDelta;
            return this;
        }
    }
}
