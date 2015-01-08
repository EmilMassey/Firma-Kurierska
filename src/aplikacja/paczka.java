package aplikacja;


public class paczka {
    private int x, y;
    private String status = "Niedostarczona";
    
    public paczka(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void zmien_status(){
        this.status = "Dostarczona"; 
    }
    
    public int zwroc_x(){
        return x;
    }
    
    public int zwroc_y(){
        return y;
    }
    
    public String zwroc_status(){
        return status;
    }
    
    public String adres(){
        if(x >= 9 && x <= 15 && y >= 9 && y <= 15){return "RONDO KURIERSKIE";}
        switch(x){
            case 2:
                if(y >= 3 && y <= 5){return "WINNA " + (y - 2);}
            break;
            case 4:
                if(y >= 7 && y <= 12){return "AL.NIEPODLEGŁOŚCI " + (y - 6);}
            break;
            case 5:
                if(y >= 0 && y <= 1){return "GĂ“RNA " + (y + 1);}
                if(y >= 12 && y <= 23){return "AL.NIEPODLEGŁOŚCI " + (y - 5);}
            break;
            case 8:
                if(y >= 19 && y <= 20){return "CICHA " + (y - 18);}
            break;
            case 10:
                if(y >= 0 && y <= 2){return "DWORCOWA " + (y + 1);}
            break;
            case 11:
                if(y >= 22 && y <= 24){return "WARSZAWSKA " + (y - 21);}
            break;
            case 12:
                if(y >= 7 && y <= 8){return "BIAŁA " + (y - 6);}
                if(y >= 16 && y <= 17){return "JASNA " + (y - 15);}
            break;
            case 17:
                if(y >= 19 && y <= 23){return "MATEJKI " + (y - 18);}
            break;
            case 18:
                if(y >= 0 && y <= 2){return "RYNKOWA " + (y + 1);}
                if(y >= 13 && y <= 17){return "SZKOLNA " + (y - 12);}
            break;
            case 20:
                if(y >= 4 && y <= 9){return "POZNAĹ�SKA " + (y - 3);}
            break;
            case 21:
                if(y >= 9 && y <= 15){return "POZNAĹ�SKA " + (y - 2);}
            break;
            case 22:
                if(y >= 19 && y <= 24){return "LEPPERA " + (y - 18);}
            break;
        }
        switch(y){
            case 2:
                if(x >= 0 && x <= 5){return "DĹ�UGA " + (x + 1);}
            break;
            case 3:
                if(x >= 10 && x <= 24){return "RUBAKA " + (x - 9);}
            break;
            case 6:
                if(x >= 0 && x <= 19){return "JANA PAWĹ�A II " + (x + 1);}
            break;
            case 9:
                if(x >= 22 && x <= 24){return "ĹšLÄ„SKA " + (x - 21);}
            break;    
            case 12:
                if(x >= 6 && x <= 8){return "CIEMNA " + (x - 5);}
                if(x >= 16 && x <= 20){return "MUZYCZNA " + (x - 15);}
            break;    
            case 15:
                if(x >= 22 && x <= 24){return "DOLNA " + (x - 21);}
            break;
            case 17:
                if(x >= 0 && x <= 4){return "3 MAJA " + (x + 1);}
            break;
            case 18:
                if(x >= 8 && x <= 24){return "KOĹšCIUSZKI " + (x - 7);}
            break;    
            case 21:
                if(x >= 6 && x <= 11){return "MOKRA " + (x - 5);}
            break;    
            case 23:
                if(x >= 0 && x <= 5){return "KOPERNIKA " + (x + 1);}
                if(x >= 18 && x <= 21){return "SZPITALNA " + (x - 17);}
            break;
        }
        return "Jeszcze tu trzeba dodaÄ‡!!";
    }
}
