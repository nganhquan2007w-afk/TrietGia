import java.util.concurrent.Semaphore;
 class TrietGia extends Thread {
     private final int id;
     private final Semaphore[] forks;
     private final Semaphore limit;
     private final boolean live = true;

     public TrietGia(int id, Semaphore[] forks, Semaphore limit) {
         this.forks = forks;
         this.id = id;
         this.limit = limit;
     }

     public void run() {
         try {
             while (live) {
                 // nghĩ
                 think();
                 //yêu cầu vào ăn
                 limit.acquire();
                 //lấy nĩa trái
                 forks[id].acquire();
                 //lấy nĩa phải
                 forks[(id + 1) % 5].acquire();
                 //có đủ nĩa thì ăn
                 eat();
                 //ăn xog trả nĩa trái
                 forks[id].release();
                 //trả nĩa phải
                 forks[(id + 1) % 5].release();
                 //rời bàn
                 limit.release();
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

     public void think() {
         System.out.println("Triết Gia " + id + " Đang Nghĩ");
     }

     public void eat() {
         System.out.println("Triết Gia " + id + " Đang Ăn");
     }
 }
    public class ThucThiTrietGia {
        public static void main(String[] args) {
            Semaphore[] forks = new Semaphore[5];
                for (int i = 0; i < 5; i ++ ) {
                    forks[i] = new Semaphore(1);
            }
                Semaphore limit = new Semaphore(4);
                for ( int i = 0; i < 5; i ++ ) {
                    new TrietGia(i, forks, limit).start();
                }
        }
    }

