[1mdiff --git a/src/main/java/com/nwb/bill/manager/BillManager.java b/src/main/java/com/nwb/bill/manager/BillManager.java[m
[1mindex 96541a0..edb9594 100644[m
[1m--- a/src/main/java/com/nwb/bill/manager/BillManager.java[m
[1m+++ b/src/main/java/com/nwb/bill/manager/BillManager.java[m
[36m@@ -1,31 +1,215 @@[m
 package com.nwb.bill.manager;[m
 import com.nwb.bill.model.Bill;[m
 [m
[32m+[m[32mimport java.io.File;[m
[32m+[m[32mimport java.text.ParseException;[m
[32m+[m[32mimport java.text.SimpleDateFormat;[m
 import java.util.*;[m
[32m+[m[32mimport java.util.concurrent.TimeUnit;[m
 [m
 public class BillManager {[m
[32m+[m	[32mprivate static int billId=0;[m
 	private String userId;[m
[31m-    private List<Bill> bills;[m
[32m+[m	[32mprivate List<Bill> bills;[m
[32m+[m	[32mScanner s=new Scanner(System.in);[m
[32m+[m	[32mpublic BillManager(String userId) {[m
[32m+[m		[32mthis.userId = userId;[m
[32m+[m		[32mthis.bills = new ArrayList<>();[m
[32m+[m	[32m}[m
 [m
[31m-    public BillManager(String userId) {[m
[31m-    	this.userId = userId;[m
[31m-        this.bills = new ArrayList<>();[m
[31m-    }[m
[32m+[m	[32mpublic List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {[m
[32m+[m		[32m//Must add some code here[m
[32m+[m		[32mreturn bills;[m
[32m+[m
[32m+[m	[32m}[m
[32m+[m
[32m+[m	[32mpublic void addNewBill(Bill bill) {[m
[32m+[m		[32m//must add some code here[m
[32m+[m		[32mSystem.out.println("Enter bill name");[m
[32m+[m		[32mString bill_name=s.nextLine();[m
[32m+[m		[32mSystem.out.println("Enter bill category");[m
[32m+[m		[32mString bill_category=s.nextLine();[m
[32m+[m		[32mSystem.out.println("Enter due date(dd-mm-yyyy)");[m
[32m+[m		[32mboolean flag=true;[m
[32m+[m		[32mDate due_date=null;[m
[32m+[m		[32mdo{[m
[32m+[m			[32mString dateInput=s.nextLine();[m
[32m+[m			[32mtry {[m
[32m+[m				[32mSimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");[m
[32m+[m				[32mdue_date = dateFormat.parse(dateInput);[m
[32m+[m				[32mflag=false;[m
[32m+[m			[32m} catch (ParseException e) {[m
[32m+[m				[32mSystem.out.println("Invalid date format. Please enter the date in dd-mm-yyyy format.");[m
[32m+[m			[32m}[m
[32m+[m		[32m}while(flag);[m
[32m+[m		[32mDate todays_date=new Date();[m
 [m
[31m-    public List<Bill> getBillsOverview(String category, Date fromDate, Date toDate, String status) {[m
[32m+[m		[32mSystem.out.println("Enter amount");[m
[32m+[m		[32mdouble amount=s.nextDouble();[m
[32m+[m		[32mSystem.out.println("Enter reminder frequency(weekly/monthly/yearly)");[m
[32m+[m		[32mString reminder_frequency=s.nextLine();[m
[32m+[m		[32mSystem.out.println("Upload attachment");[m
[32m+[m		[32mFile attachment=new File("C:\\Users\\bhatt\\Downloads\\Documents\\PayPilot");[m
[32m+[m		[32mif(!attachment.exists()) {[m
[32m+[m			[32mSystem.out.println("Invalid file path");[m
[32m+[m		[32m}[m
[32m+[m		[32mSystem.out.println("Do you want to add any notes?(y/n)");[m
[32m+[m		[32mchar notes_response=s.next().charAt(0);[m
[32m+[m		[32mnotes_response=Character.toLowerCase(notes_response);[m
[32m+[m		[32mString notes="";[m
[32m+[m		[32mif(notes_response=='y') {[m
[32m+[m			[32mSystem.out.print("Write notes: ");[m
[32m+[m			[32mnotes=s.nextLine();[m
[32m+[m		[32m}[m
[32m+[m		[32mSystem.out.println("Do you want to toggle recurring bill(y/n)");[m
[32m+[m		[32mchar rec_bill=s.next().charAt(0);[m
[32m+[m		[32mrec_bill=Character.toLowerCase(rec_bill);[m
[32m+[m		[32mSystem.out.println("Do you want to save?(y/n)");[m
[32m+[m		[32mchar save_response=s.next().charAt(0);[m
[32m+[m		[32msave_response=Character.toLowerCase(save_response);[m
[32m+[m		[32mif(save_response=='y') {[m
[32m+[m			[32mBill newBill=new Bill();[m
[32m+[m			[32mbill.setBillName(bill_name);[m
[32m+[m			[32mbill.setBillCategory(bill_category);[m
[32m+[m			[32mbill.setDueDate(due_date);[m
[32m+[m			[32mbill.setAmount(amount);[m
[32m+[m			[32mbill.setReminderFrequency(reminder_frequency);[m
[32m+[m			[32mbill.setAttachment(attachment);[m
[32m+[m			[32mbill.setNotes(notes);[m
[32m+[m			[32mif(rec_bill=='y') {[m
[32m+[m				[32mbill.setRecurring(true);[m
[32m+[m			[32m}else {[m
[32m+[m				[32mbill.setRecurring(false);[m
[32m+[m			[32m}[m
[32m+[m			[32mbill.setBillId(++billId);[m
[32m+[m			[32mbill.setPaymentStatus("Not Paid");[m
[32m+[m			[32mif(todays_date.compareTo(due_date)>0) {[m
[32m+[m				[32mlong diffInMillis = Math.abs(todays_date.getTime() - due_date.getTime());[m
[32m+[m				[32mlong diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);[m
[32m+[m				[32mbill.setOverdueDays((int)diffInDays);[m
[32m+[m			[32m}else {[m
[32m+[m				[32mbill.setOverdueDays(0);[m
[32m+[m			[32m}[m
[32m+[m			[32mbills.add(newBill);[m
[32m+[m			[32mSystem.out.println("Bill saved successfully!!");[m
[32m+[m		[32m}else {[m
[32m+[m			[32mSystem.out.println("Bill not saved.");[m
[32m+[m		[32m}[m
[32m+[m	[32m}[m
[32m+[m
[32m+[m	[32mpublic List<Bill> getOverdueBills(List<Bill> bills) {[m
 		//Must add some code here[m
[31m-    	return bills;[m
[31m-        [m
[31m-    }[m
[31m-[m
[31m-    public void addNewBill(Bill bill) {[m
[31m-        //must add some code here[m
[31m-    }[m
[31m-[m
[31m-    public List<Bill> getOverdueBills() {[m
[31m-    	//Must add some code here[m
[31m-    	return bills;[m
[31m-        [m
[31m-    }[m
[32m+[m		[32mList<Bill> overdueBills=new ArrayList<>();[m
[32m+[m		[32mSystem.out.println("Select if you\n1. Want to filter out or\n2. You want "+[m
[32m+[m				[32m"to enter the details manually");[m
[32m+[m		[32mswitch(s.nextInt()) {[m
[32m+[m			[32mcase 1:[m
[32m+[m				[32mSystem.out.println("Select one of the two options:\n"+[m
[32m+[m						[32m"1. By date range\n2. By bill category");[m
[32m+[m				[32mswitch(s.nextInt()) {[m
[32m+[m					[32mcase 1:[m
[32m+[m						[32mboolean flag=true;[m
[32m+[m						[32mDate date_start=null;[m
[32m+[m						[32mDate date_end=null;[m
[32m+[m						[32mdo {[m
[32m+[m							[32mSystem.out.println("Enter start date(dd-mm-yyyy)");[m
[32m+[m							[32mString start_date=s.nextLine();[m
[32m+[m							[32mSystem.out.println("Enter end date(dd-mm-yyyy)");[m
[32m+[m							[32mString end_date=s.nextLine();[m
[32m+[m							[32mSimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");[m
[32m+[m							[32mtry {[m
[32m+[m								[32mdate_start=sdf.parse(start_date);[m
[32m+[m								[32mdate_end=sdf.parse(end_date);[m
[32m+[m								[32mflag=false;[m
[32m+[m							[32m}catch(Exception e) {[m
[32m+[m								[32mSystem.out.println("Invalid date entered!! Try again");[m
[32m+[m							[32m}[m
[32m+[m
[32m+[m						[32m}while(flag);[m
[32m+[m						[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m							[32mBill curr_bill=bills.get(i);[m
[32m+[m							[32mDate bill_date=curr_bill.getDueDate();[m
[32m+[m[41m							[m
[32m+[m							[32mif(date_start.compareTo(bill_date)<=0 &&[m[41m [m
[32m+[m									[32mdate_end.compareTo(bill_date)>=0) {[m
[32m+[m								[32moverdueBills.add(curr_bill);[m
[32m+[m							[32m}[m
[32m+[m						[32m}[m
[32m+[m						[32mbreak;[m
[32m+[m					[32mcase 2:[m
[32m+[m						[32mSystem.out.println("Enter bill category");[m
[32m+[m						[32mString bill_category=s.nextLine();[m
[32m+[m						[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m							[32mif(bills.get(i).getBillCategory().trim().equals(bill_category)) {[m
[32m+[m								[32moverdueBills.add(bills.get(i));[m
[32m+[m							[32m}[m
[32m+[m						[32m}[m
[32m+[m						[32mbreak;[m
[32m+[m					[32mdefault: System.out.println("Wrong Choice!!");[m
[32m+[m					[32mbreak;[m
[32m+[m				[32m}[m
[32m+[m				[32mbreak;[m
[32m+[m			[32mcase 2:[m
[32m+[m				[32mSystem.out.println("Select bill category\n1. House Rent\n2. Debt Payments\n"+[m
[32m+[m						[32m"3. Groceries\n4. Internet Charges\n5. Cellphone Charges");[m
[32m+[m				[32mint bill_category=s.nextInt();[m
[32m+[m				[32mSystem.out.println("Enter bill name");[m
[32m+[m				[32mString bill_name=s.nextLine();[m
[32m+[m				[32mswitch(bill_category) {[m
[32m+[m				[32mcase 1:[m[41m [m
[32m+[m					[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m						[32mif(bills.get(i).getBillCategory().trim().equals("House Rent")[m
[32m+[m								[32m&& bills.get(i).getBillName().trim().equals(bill_name)) {[m
[32m+[m							[32moverdueBills.add(bills.get(i));[m
[32m+[m							[32mbreak;[m
[32m+[m						[32m}[m
[32m+[m					[32m}[m
[32m+[m					[32mbreak;[m
[32m+[m				[32mcase 2:[m[41m [m
[32m+[m					[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m						[32mif(bills.get(i).getBillCategory().trim().equals("Debt Payments")[m
[32m+[m								[32m&& bills.get(i).getBillName().trim().equals(bill_name)) {[m
[32m+[m							[32moverdueBills.add(bills.get(i));[m
[32m+[m							[32mbreak;[m
[32m+[m						[32m}[m
[32m+[m					[32m}[m
[32m+[m					[32mbreak;[m
[32m+[m				[32mcase 3:[m
[32m+[m					[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m						[32mif(bills.get(i).getBillCategory().trim().equals("Groceries")[m
[32m+[m								[32m&& bills.get(i).getBillName().trim().equals(bill_name)) {[m
[32m+[m							[32moverdueBills.add(bills.get(i));[m
[32m+[m							[32mbreak;[m
[32m+[m						[32m}[m
[32m+[m					[32m}[m
[32m+[m					[32mbreak;[m
[32m+[m				[32mcase 4:[m
[32m+[m					[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m						[32mif(bills.get(i).getBillCategory().trim().equals("Internet Charges")[m
[32m+[m								[32m&& bills.get(i).getBillName().trim().equals(bill_name)) {[m
[32m+[m							[32moverdueBills.add(bills.get(i));[m
[32m+[m							[32mbreak;[m
[32m+[m						[32m}[m
[32m+[m					[32m}[m
[32m+[m					[32mbreak;[m
[32m+[m				[32mcase 5:[m
[32m+[m					[32mfor(int i=0;i<bills.size();i++) {[m
[32m+[m						[32mif(bills.get(i).getBillCategory().trim().equals("Cellphone Charges")[m
[32m+[m								[32m&& bills.get(i).getBillName().trim().equals(bill_name)) {[m
[32m+[m							[32moverdueBills.add(bills.get(i));[m
[32m+[m							[32mbreak;[m
[32m+[m						[32m}[m
[32m+[m					[32m}[m
[32m+[m					[32mbreak;[m
[32m+[m				[32mdefault: System.out.println("Wrong Choice!!");[m
[32m+[m				[32mbreak;[m
[32m+[m				[32m}[m
[32m+[m				[32mbreak;[m
[32m+[m			[32mdefault: System.out.println("Wrong Choice!!");[m
[32m+[m			[32mbreak;[m
[32m+[m		[32m}[m
[32m+[m		[32mreturn overdueBills;[m
[32m+[m
[32m+[m	[32m}[m
 }[m
 [m
[1mdiff --git a/src/main/java/com/nwb/bill/model/Bill.java b/src/main/java/com/nwb/bill/model/Bill.java[m
[1mindex 65a0a03..c5f4b1f 100644[m
[1m--- a/src/main/java/com/nwb/bill/model/Bill.java[m
[1m+++ b/src/main/java/com/nwb/bill/model/Bill.java[m
[36m@@ -13,7 +13,7 @@[m [mpublic class Bill {[m
     private String billName;                [m
     private String billCategory;            [m
     private Date dueDate;                   [m
[31m-    private double amount;                  [m
[32m+[m[32m    private double amount;[m[41m            [m
     private String reminderFrequency;       [m
     private File attachment;                [m
     private String notes;                   [m
[1mdiff --git a/target/classes/.gitignore b/target/classes/.gitignore[m
[1mnew file mode 100644[m
[1mindex 0000000..c2d9872[m
[1m--- /dev/null[m
[1m+++ b/target/classes/.gitignore[m
[36m@@ -0,0 +1 @@[m
[32m+[m[32m/com/[m
[1mdiff --git a/target/classes/META-INF/MANIFEST.MF b/target/classes/META-INF/MANIFEST.MF[m
[1mindex 60ca6fd..93cb077 100644[m
[1m--- a/target/classes/META-INF/MANIFEST.MF[m
[1m+++ b/target/classes/META-INF/MANIFEST.MF[m
[36m@@ -1,5 +1,5 @@[m
 Manifest-Version: 1.0[m
[31m-Built-By: Dell[m
[32m+[m[32mBuilt-By: bhatt[m
 Build-Jdk: 17.0.5[m
 Created-By: Maven Integration for Eclipse[m
 [m
[1mdiff --git a/target/classes/META-INF/maven/com.basics/controller/pom.properties b/target/classes/META-INF/maven/com.basics/controller/pom.properties[m
[1mindex 8ae330f..98c7367 100644[m
[1m--- a/target/classes/META-INF/maven/com.basics/controller/pom.properties[m
[1m+++ b/target/classes/META-INF/maven/com.basics/controller/pom.properties[m
[36m@@ -1,7 +1,7 @@[m
 #Generated by Maven Integration for Eclipse[m
[31m-#Sat Aug 10 18:55:16 IST 2024[m
[31m-m2e.projectLocation=C\:\\Users\\Dell\\Documents\\Eclipse Workspace\\PayPilotBills[m
[31m-m2e.projectName=PayPilotBills[m
[32m+[m[32m#Sat Aug 10 19:13:06 IST 2024[m
[32m+[m[32mm2e.projectLocation=C\:\\Users\\bhatt\\eclipse-workspace\\PayPilot[m
[32m+[m[32mm2e.projectName=PayPilot[m
 groupId=com.basics[m
 artifactId=controller[m
 version=0.0.1-SNAPSHOT[m
[1mdiff --git a/target/classes/com/nwb/bill/manager/BillManager.class b/target/classes/com/nwb/bill/manager/BillManager.class[m
[1mindex 59a179e..e61c97a 100644[m
Binary files a/target/classes/com/nwb/bill/manager/BillManager.class and b/target/classes/com/nwb/bill/manager/BillManager.class differ
