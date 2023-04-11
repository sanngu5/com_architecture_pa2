package Test;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

public class Test {
	public static float LRU(int[] lru, int set, int len_slot) {
        System.out.println("==============================");
        System.out.println("LRU알고리즘 시작");
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        int slot[]=new int[len_slot];//슬롯 개수를 배열 크기로 하여 배열 선언
        //int count[]=new int[len_slot];
        int check=0;//적중했는지 미스했는지
        int number=0;//적중률 계산 때문에
        for(int s=0;s<len_slot;s++) {
           slot[s]=-1;//배열 초기값을 -1로 설정
           //System.out.print(slot[s]+" ");
        }
        for(int i=0;i<set;i++) {
           check=0;
           for(int j=0;j<len_slot;j++) {//적중
              if(lru[i]==slot[j]) {
                 check=1;
                 int temp=slot[j];
                 for(int s=j;s>=1;s--) {
                    slot[s]=slot[s-1];
                 }
                 slot[0]=temp;
                 number++;
                 break;
              }
           }
           if(check==0) {//미스
              for(int k=len_slot-1;k>=1;k--) {
                 slot[k]=slot[k-1];
              }
              slot[0]=lru[i];
           }

           for(int s=0;s<len_slot;s++) {
              System.out.print(slot[s]+" ");

           }
           if(check!=0) {
              System.out.print(" 적중");
           }
           System.out.println();
        }
        /*System.out.println(number);*/
        
        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        System.out.println("알고리즘 수행시간(ms) : "+secDiffTime);
        
        float percent=(float)number/set;//적중률 계산
        return percent;//적중률을 리턴

     }    


     public static float FIFO(int[] fifo, int len_slot) {
           System.out.println("==============================");
           System.out.println("FIFO알고리즘 시작 ('>' 뒤의 값이 가장 오래된 값)");

           int[] slot = new int[len_slot];
           int hit = 0;

           long beforeTime = System.currentTimeMillis();

           int loaded = 0;
           int oldestSlot = 0;

           for (int i = 0; i < fifo.length; i++) {
              // 데이터 탐색
              boolean found = false;
              for (int j = 0; j < loaded; j++) {
                 if (slot[j] == fifo[i]) {
                    // 캐쉬 적중
                    found = true;
                    hit++;
                    break;
                 }
              }
              if (!found && loaded < len_slot) {
                 // 캐쉬 미스, 빈 슬롯 있음 -> 빈 슬롯에 데이터 적재
                 slot[loaded++] = fifo[i];
              } else if(!found && loaded >= len_slot) {
                 // 캐쉬 미스, 빈 슬롯 없음 -> 가장 오래된 슬롯 교체
                 slot[oldestSlot] = fifo[i];
                 oldestSlot = (oldestSlot + 1) % len_slot;   // 교체 후 가장 오래된 슬롯의 인덱스를 oldestSlot에 저장
              }

              // 접근 후 캐쉬 상태 출력
              for (int j = 0; j < len_slot; j++) {
                 if (j == oldestSlot) {
                    // '>' 뒤의 값이 가장 오래된 값
                    System.out.print(">");
                 }
                 System.out.print(slot[j] + " ");
              }
              
              if (found) {
                 System.out.print("적중");
              }
              System.out.println();
           }

           // 수행 시간 출력
           beforeTime = System.currentTimeMillis() - beforeTime;
           System.out.println("알고리즘 수행시간(ms) : " + beforeTime);

           // 적중률 반환
           return (float) hit / fifo.length;
        }
     

     public static float RANDOM(int[] accessData, int len_slot) {
        System.out.println("==============================");
        System.out.println("RANDOM알고리즘 시작");
        
        Scanner in = new Scanner(System.in);
        Random r = new Random();

        int[] slot = new int[len_slot];
        int hit = 0;
        long totalTimeDelta = 0;

        // 여러 번 수행하여 평균을 취할 수 있도록 구현
        System.out.print("수행 횟수를 입력하세요: ");
        int testCount = in.nextInt();

        for (int test = 0; test < testCount; test++) {
           
           // 슬롯 -1로 초기화
           for(int i = 0; i < len_slot; i++) {
              slot[i] = -1;
           }
           
           System.out.println("\n# 수행 " + (test + 1) + "회차");
           long timeDelta = System.currentTimeMillis();
           int loaded = 0;
           
           for (int data : accessData) {
              boolean found = false;
              int i;
              for (i = 0; i < loaded; i++) {
                 // 캐쉬에서 데이터 탐색
                 if (slot[i] == data) {
                    // 캐쉬 적중
                    found = true;
                    hit++;
                    break;
                 }
              }
              if (!found && loaded < len_slot) {
                 // 캐쉬 미스, 캐쉬 빈 공간 있음 -> 적재
                 slot[loaded++] = data;
              } else if(!found && loaded >= len_slot) {
                 // 캐쉬 미스, 캐쉬 빈 공간 없음 -> 임의 교체
                 slot[r.nextInt(slot.length)] = data;
              }

              // 접근 후 캐쉬 상태 출력
              for (int s : slot) {
                 System.out.print(s + " ");
              }
              if (found) {
                 System.out.print("적중");
              }
              System.out.println();
           }
           // 단일 수행 시간 계산
           totalTimeDelta += System.currentTimeMillis() - timeDelta;
        }

        // 평균 수행 시간 출력
        System.out.println("\n알고리즘 수행 시간(ms) : 평균 " + ((float) totalTimeDelta / testCount) + " (" + testCount + "회 수행)");

        // 적중률 반환
        return (float) hit / accessData.length / testCount;
     }
     
     public static float LFU(int[] lfu, int set, int len_slot) {
          System.out.println("==============================");
          System.out.println("LFU알고리즘 시작");
          long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
          
          LinkedList hit = new LinkedList(); //적중한 데이터 번호 저장
          int slot[]=new int[len_slot];   //슬롯
          int count[] = new int[len_slot];   //슬롯별 참조수 카운터 값
          int fifo_count[] = new int[len_slot];   //선입선출을 위한 카운터
           
          for(int i=0;i<slot.length;i++) { //슬롯 -1로 초기화, 카운터는 -1으로 초기화
             slot[i] = -1;
             count[i] = -1;
             fifo_count[i] = -1;
          }
          
          for(int i = 0; i < lfu.length; i++)   {   //데이터 하나씩 검사
             boolean whit = false;
             int min_slot = len_slot;        
             int min_count = lfu.length;
             int min_fifo = len_slot;
             for(int j = 0; j < slot.length; j++){   //슬롯 하나씩 검사
                if(lfu[i] == slot[j]){   //적중
                  count[j]++;		
                  whit = true;
                  hit.add(i);
                  break;
                }
                else {               //미스
                   if(slot[j] == -1) {   //미스이면서 슬롯 빈 경우 
                      min_slot = j;
                      break;
                   }
                   else if((int)count[j] < min_count) {      
                      min_slot = j;
                      min_count = (int)count[j];
                      min_fifo = fifo_count[j];
                   }
                   else if((int)count[j] == min_count && fifo_count[j] < min_fifo) {
                      min_slot = j;
                      min_count = (int)count[j];
                      min_fifo = fifo_count[j];
                   }
                }
             }
             if(whit == true) {   //적중
                for(int s=0;s<len_slot;s++) 
                       System.out.print(slot[s]+" ");
                System.out.print(" 적중");
                System.out.println();
             }
             else if(whit == false) {         //미스
                slot[min_slot] = lfu[i];
                fifo_count[min_slot] = i;
                count[min_slot] = 0;
                for(int s=0;s<len_slot;s++) 
                       System.out.print(slot[s]+" ");
                System.out.println();
             }
          }
     
          long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
          long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
          System.out.println("알고리즘 수행시간(ms) : "+secDiffTime);
          return (float)hit.size()/lfu.length; //적중률 리턴
        }
     
     public static float LFU2 (int[] lfu, int set, int len_slot) {
         System.out.println("==============================");
         System.out.println("LFU2알고리즘 시작");
         long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
         
         LinkedList hit = new LinkedList(); //적중한 데이터 번호 저장
         int slot[]=new int[len_slot];   //슬롯
         float count[] = new float[len_slot];   //슬롯별 참조수 카운터 값
         int fifo_count[] = new int[len_slot];   //선입선출을 위한 카운터
          
         for(int i=0;i<slot.length;i++) { //슬롯 -1로 초기화, 카운터는 -1으로 초기화
            slot[i] = -1;
            count[i] = -1;
            fifo_count[i] = -1;
         }
         
         for(int i = 0; i < lfu.length; i++)   {   //데이터 하나씩 검사
            boolean whit = false;
            int min_slot = len_slot;        
            int min_count = lfu.length;
            int min_fifo = len_slot;
            for(int j = 0; j < slot.length; j++){   //슬롯 하나씩 검사
               if(lfu[i] == slot[j]){   //적중
                 count[j] = (float) (count[j] + 0.5);		// @@@ 이부분 + 0.5로 바꾸기만 하시면 됩니다 @@@
                 whit = true;
                 hit.add(i);
                 break;
               }
               else {               //미스
                  if(slot[j] == -1) {   //미스이면서 슬롯 빈 경우 
                     min_slot = j;
                     break;
                  }
                  else if((int)count[j] < min_count) {      
                     min_slot = j;
                     min_count = (int)count[j];
                     min_fifo = fifo_count[j];
                  }
                  else if((int)count[j] == min_count && fifo_count[j] < min_fifo) {
                     min_slot = j;
                     min_count = (int)count[j];
                     min_fifo = fifo_count[j];
                  }
               }
            }
            if(whit == true) {   //적중
               for(int s=0;s<len_slot;s++) 
                      System.out.print(slot[s]+" ");
               System.out.print(" 적중");
               System.out.println();
            }
            else if(whit == false) {         //미스
               slot[min_slot] = lfu[i];
               fifo_count[min_slot] = i;
               count[min_slot] = 0;
               for(int s=0;s<len_slot;s++) 
                      System.out.print(slot[s]+" ");
               System.out.println();
            }
         }
    
         long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
         long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
         System.out.println("알고리즘 수행시간(ms) : "+secDiffTime);
         return (float)hit.size()/lfu.length; //적중률 리턴
       }

     public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in); //공통 스캐너

        while(true) {
           //사용자 입력 
           System.out.println("LRU, FIFO, RANDOM, LFU, LFU2 순서대로 진행 됩니다.");
           System.out.println("데이터 셋의 크기를 입력하세요(종료하려면 0을 입력)");
           int len_set=scanner.nextInt();
           if(len_set==0) {
              System.out.println("종료합니다");
              break;
           }
           
           System.out.println("데이터를 입력하세요.");
           int num[] = new int[len_set];

           for(int i=0;i<num.length;i++) {
              num[i]=scanner.nextInt();
           }
           
           System.out.println("슬롯의 수를 입력하세요.");
           Scanner scan=new Scanner(System.in);
           int len_slot=scan.nextInt();
           
           //테스트용 난수 발생
           /*Random random=new Random();
           int len_set=100; //참조 데이터 개수
           int len_slot=5; //슬롯 개수
           int num[]=new int [len_set];
           for(int i=0;i<num.length;i++) {
              int r=random.nextInt(5); //()안에 데이터 범위 입력
               num[i]=r; 
               System.out.print(r+" ");
           }
           System.out.println();*/
            
           
           System.out.println("적중률 : " + LRU(num, len_set, len_slot));
           System.out.println("적중률 : " + FIFO(num, len_slot));
           System.out.println("적중률 : " + RANDOM(num, len_slot));
           System.out.println("적중률 : " + LFU(num, len_set, len_slot));
           System.out.println("적중률 : " + LFU2(num, len_set, len_slot));

        }//while문 종료
     }//main 종료
}