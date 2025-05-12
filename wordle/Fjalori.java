public class Fjalori {
    private String fjalaEsakte;
    private String shpjegimi;

    public Fjalori(){
        
    }
    public Fjalori(String fjalaEsakte,String shpjegimi){
        this.fjalaEsakte=fjalaEsakte;
        this.shpjegimi=shpjegimi;
    }

    public String getFjalaEsakte() {
        return fjalaEsakte;
    }

    public String getShpjegimi() {
        return shpjegimi;
    }

    public void setFjalaEsakte(String fjalaEsakte) {
        this.fjalaEsakte = fjalaEsakte;
    }
    
    public void setShpjegimi(String shpjegimi) {
        this.shpjegimi = shpjegimi;
    }
}
