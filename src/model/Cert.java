package model;

public class Cert {

        private int certId;
        private int employeeId;
        private String certName;

        public Cert(int certId, int employeeId, String certName) {
            this.certId = certId;
            this.employeeId = employeeId;
            this.certName = certName;
        }

        public int getCertId() {
            return certId;
        }

        public void setCertId(int certId) {
            this.certId = certId;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getCertName() {
            return certName;
        }

        public void setCertName(String certName) {
            this.certName = certName;
        }
}
