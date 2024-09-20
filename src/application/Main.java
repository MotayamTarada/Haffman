package application;
	

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import application.Main.BitInputStream;
import application.Main.BitOutputStream;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;



public class Main extends Application {
	 public Label lb0 = new Label("");
	 public Label lb1 = new Label("");
	 public Label lb2 = new Label("");
	 public StringBuilder sba = new StringBuilder();
	 public HBox aaa = new HBox();
	 public VBox aaa1 = new VBox();
	 public Scene scene;
	 public Scene scene1;
	 public Button back;
	 public ScrollPane pane2;
	 public int lineinheader;
	 public long fileHuf;
	 public String[] encodings;
	 public TableView<ObservableList<String>> tableView;
	 public ObservableList<ObservableList<String>> dataa;
	 public MinHeapSort <freqData>minHeapSort;
	 public MinHeap<Integer> heap;
	 public String storePreTree;
	 public StringBuilder preorderStringBuilder;

	 public Main() {
	     this.scene = new Scene(this.aaa, 900.0, 900.0);
	     this.scene1 = new Scene(this.aaa1, 900.0, 500.0);
	     this.back = new Button("Back");
	     this.pane2 = new ScrollPane();
	     this.encodings = new String[256];
	     this.tableView = new TableView();
	     this.dataa = FXCollections.observableArrayList();
	     this.heap = new MinHeap<Integer>(256);
	     this.storePreTree = "";
	     this.preorderStringBuilder = new StringBuilder();
	 }

	 public static void createTable(TableView<ObservableList<String>> tableView, ObservableList<ObservableList<String>> data) {
	     TableColumn<ObservableList<String>, String> charColumn = createColumn("Char", 0);
	     TableColumn<ObservableList<String>, String> freqColumn = createColumn("Freq", 1);
	     TableColumn<ObservableList<String>, String> hCodeColumn = createColumn("Hcode", 2);
	     TableColumn<ObservableList<String>, String> lengthColumn = createColumn("Length", 3);
	     tableView.getColumns().addAll(new TableColumn[]{charColumn, freqColumn, hCodeColumn, lengthColumn});
	     tableView.setItems(data);
	 }

	 private static TableColumn<ObservableList<String>, String> createColumn(String columnName, int index) {
	     TableColumn<ObservableList<String>, String> column = new TableColumn(columnName);
	     column.setCellValueFactory((cellData) -> {
	         return new SimpleStringProperty((String)((ObservableList)cellData.getValue()).get(index));
	     });
	     return column;
	 }

	@Override
	public void start(Stage primaryStage) {
		try {
			
			
		    int[] num = new int[256];
		     Button back = new Button("Back");
		     new BorderPane();
		     Line line1 = new Line();
		     Line line2 = new Line();
		     new Line();
		     line1.setStartX(0.0);
		     line1.setEndX(900.0);
		     line1.setStroke(Color.WHITE);
		     line1.setStrokeWidth(5.0);
		     line2.setStartX(0.0);
		     line2.setEndX(900.0);
		     line2.setStroke(Color.WHITE);
		     line2.setStrokeWidth(5.0);
		     HBox Buttons = new HBox();
		     Buttons.setStyle("-fx-spacing: 15; -fx-padding: 15;");
		     Buttons.setAlignment(Pos.CENTER);
		     HBox lButtons = new HBox();
		     lButtons.setStyle("-fx-spacing: 15; -fx-padding: 15;");
		     lButtons.setAlignment(Pos.CENTER);
		     Button sheader = new Button("Header");
		     sheader.setPrefSize(150.0, 70.0);
		     sheader.setPadding(new Insets(10.0, 15.0, 10.0, 15.0));
		     sheader.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #0766AD;-fx-text-fill: #ffffff ; -fx-border-color: derive(#ff0000, +100%);");
		     Button stable = new Button("Table");
		     stable.setPrefSize(150.0, 70.0);
		     stable.setPadding(new Insets(20.0, 30.0, 20.0, 30.0));
		     stable.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #000000 ; -fx-border-color: derive(#ff0000, +100%);");
		     lButtons.getChildren().addAll(new Node[]{sheader});
		     Button load = new Button("Compress");
		     load.setPadding(new Insets(20.0, 70.0, 20.0, 70.0));
		     load.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #000000 ; -fx-border-radius: 100; -fx-border-color: derive(#ff0000, +100%);");
		     Button run = new Button("Decompress");
		     run.setPadding(new Insets(20.0, 70.0, 20.0, 70.0));
		     run.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #000000; -fx-border-radius: 100; -fx-border-color: derive(#ff0000, +100%);");
		     back.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #0766AD;-fx-text-fill: #ffffff ; -fx-border-color: derive(#ff0000, +100%);");
		     VBox labelsVB = new VBox();
		     this.lb0.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #E3651DFF ; ");
		     this.lb1.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #E3651DFF ; ");
		     this.lb2.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #E3651DFF ; ");
		     Label lb3 = new Label("The  Header");
		     lb3.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #E3651DFF ; -fx-border-color: derive(#E3651DFF, +100%);");
		     TextArea textAreaX = new TextArea();
		     textAreaX.setPromptText("Header");
		     textAreaX.setEditable(false);
		     textAreaX.setPadding(new Insets(2.0, 2.0, 2.0, 2.0));
		     textAreaX.setPrefSize(500.0, 500.0);
		     textAreaX.setMinSize(150.0, 100.0);
		     textAreaX.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #000000 ; ");
		     HBox Areas = new HBox();
		     Areas.setStyle("-fx-spacing: 15; -fx-padding: 10;");
		     this.tableView.setStyle("-fx-font-family: 'Roboto Slab ExtraBold';-fx-font-size: 18; -fx-base: #e3651d;-fx-text-fill: #000000 ; -fx-border-radius: 100; -fx-border-color: derive(#ff0000, +100%);");
		     this.tableView.setPrefSize(400.0, 500.0);
		     Areas.setStyle("-fx-spacing: 25; -fx-padding: 15;");
		     Areas.getChildren().addAll(new Node[]{labelsVB, this.tableView});
		     Areas.setAlignment(Pos.CENTER);
		     Buttons.getChildren().addAll(new Node[]{load, run});
		     primaryStage.setTitle("Huffman");
		     VBox layout = new VBox(10.0);
		     layout.setPadding(new Insets(10.0));
		     layout.getChildren().addAll(new Node[]{Buttons, line1, Areas, line2, lButtons});
		     layout.setAlignment(Pos.TOP_CENTER);
		     layout.setStyle("-fx-background-color: #191919;");
		     load.setOnAction((e) -> {
		         this.dataa.clear();
		         this.tableView.getColumns().clear();
		         this.lb0.setText("");
		         this.lb1.setText("");
		         this.lb2.setText("");
		         textAreaX.clear();
		         this.sba = new StringBuilder("");
		         this.encodings = new String[256];
		         this.heap = new MinHeap(256);
		         this.minHeapSort = new MinHeapSort(256);
		         this.storePreTree = "";
		         FileChooser fc = new FileChooser();
		         Window stage2 = null;
		         File inputFile = fc.showOpenDialog((Window)stage2);
		         if (inputFile == null) {
		             Alert alertxx = new Alert(AlertType.ERROR);
		             alertxx.setTitle("File Error");
		             alertxx.setHeaderText((String)null);
		             alertxx.setContentText("You didn't select a file.");
		             Label contentLabelx = (Label)alertxx.getDialogPane().lookup(".content.label");
		             contentLabelx.setFont(Font.font("Roboto Slab ExtraBold", 14.0));
		             contentLabelx.setTextFill(Color.BLACK);
		             alertxx.getDialogPane().setStyle("-fx-background-color: #ffb38f;");
		             Region dialogContent = (Region)alertxx.getDialogPane().getChildren().get(2);
		             alertxx.show();
		         } else {
		             long fileHuf = inputFile.length();
		             String outputFile = changeFileExtension(inputFile.getPath(), "Huf");
		             this.lb0.setText("Original File Size: " + fileHuf);
		             if (inputFile != null) {
		                 Alert alertx;
		                 Label contentLabelxx;
		                 Region dialogContentxx;
		                 try {
		                     BufferedInputStream br = new BufferedInputStream(new FileInputStream(inputFile));
		                     new BufferedWriter(new FileWriter(outputFile));

		                     int line;
		                     while((line = br.read()) != -1) {
		                         int var10002 = num[line]++;
		                     }

		                     br.close();

		                     TNode Z;
		                     int i;
		                     for(i = 0; i < num.length; ++i) {
		                         if (num[i] != 0) {
		                             this.heap.add(num[i]);
		                             Z = new TNode((Comparable) new freqData(num[i], (char)i));
		                             this.minHeapSort.insert(Z);
		                         }
		                     }

		                     this.minHeapSort.heapSortDescending();
		                     this.heap.clear();

		                     for(i = 0; i < this.minHeapSort.heapSortDescending().length; ++i) {
		                         if (this.minHeapSort.getSize() > 1) {
		                             Z = new TNode(new freqData());
		                             TNode<freqData> x = this.minHeapSort.removeMin();
		                             TNode<freqData> y = this.minHeapSort.removeMin();
		                             Z.setLeft(x);
		                             Z.setRight(y);
		                             ((freqData)Z.data).freq = ((freqData)x.data).freq + ((freqData)y.data).freq;
		                             this.minHeapSort.insert(Z);
		                         }
		                     }

		                     BitOutputStream output = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile, true)));

		                     try {
		                         generateHuffmanCodes(this.minHeapSort.getNode(0), "", this.encodings);
		                         System.out.println(outputFile);
		                         this.writeTreeToFile(getFileExtension(inputFile.getName()), outputFile, fileHuf);
		                         this.sba.append(this.writeTreeToFile(getFileExtension(inputFile.getName()), outputFile, fileHuf));
		                         writeCompressedFile(inputFile.getPath(), output, this.encodings);
		                         File FA = new File(outputFile);
		                         this.lb1.setText("Compressed File Size: " + FA.length());
		                         System.out.println(fileHuf);
		                         System.out.println(FA.length());
		                         float compressionRatio = (float)FA.length() / (float)fileHuf;
		                         System.out.println("" + compressionRatio);
		                         this.lb2.setText("Compression Ratio: " + (100.0F - compressionRatio * 100.0F) + "%");
		                     } catch (Throwable var18) {
		                         try {
		                             output.close();
		                            
		                         } catch (Throwable var17) {
		                             var18.addSuppressed(var17);
		                         }

		                         throw var18;
		                     }

		                     output.close();

		                     for(i = 0; i < num.length; ++i) {
		                         if (num[i] != 0) {
		                             char currentChar = (char)i;
		                             ObservableList<String> row = FXCollections.observableArrayList(new String[]{String.valueOf(currentChar), String.valueOf(num[i]), this.encodings[i], String.valueOf(calculateBitLength(this.encodings[i]))});
		                             this.dataa.add(row);
		                         }
		                     }

		                     createTable(this.tableView, this.dataa);
		                     Alert alert = new Alert(AlertType.INFORMATION);
		                     alert.setTitle("Message !!!!");
		                     alert.setHeaderText((String)null);
		                     alert.setContentText("The File Has Compressed Successfully !!!");
		                     Label contentLabel = (Label)alert.getDialogPane().lookup(".content.label");
		                     contentLabel.setFont(Font.font("Roboto Slab ExtraBold", 18.0));
		                     contentLabel.setTextFill(Color.WHITE);
		                     alert.getDialogPane().setStyle("-fx-background-color: #0766ADFF;");
		                     Region dialogContentx = (Region)alert.getDialogPane().getChildren().get(2);
		                     alert.show();
		                     br.close();
		                 } catch (IOException var19) {
		                     alertx = new Alert(AlertType.ERROR);
		                     alertx.setTitle("File Error");
		                     alertx.setHeaderText((String)null);
		                     alertx.setContentText("Error");
		                     contentLabelxx = (Label)alertx.getDialogPane().lookup(".content.label");
		                     contentLabelxx.setFont(Font.font("Roboto Slab ExtraBold", 14.0));
		                     contentLabelxx.setTextFill(Color.BLACK);
		                     alertx.getDialogPane().setStyle("-fx-background-color: #ff9b66;");
		                     dialogContentxx = (Region)alertx.getDialogPane().getChildren().get(2);
		                     alertx.show();
		                     return;
		                 } catch (Exception var20) {
		                     alertx = new Alert(AlertType.ERROR);
		                     alertx.setTitle("File Error");
		                     alertx.setHeaderText((String)null);
		                     alertx.setContentText(var20.getMessage()); // Wrongggggggggggg
		                     System.err.println( var20.getMessage());
		                     contentLabelxx = (Label)alertx.getDialogPane().lookup(".content.label");
		                     contentLabelxx.setFont(Font.font("Roboto Slab ExtraBold", 14.0));
		                     contentLabelxx.setTextFill(Color.BLACK);
		                     alertx.getDialogPane().setStyle("-fx-background-color: #ffbd94;");
		                     dialogContentxx = (Region)alertx.getDialogPane().getChildren().get(2);
		                     alertx.show();
		                     return;
		                 }
		             }

		         }
		     });
		     run.setOnAction((pp) -> {
		         FileChooser fileChooser = new FileChooser();
		         fileChooser.setTitle("Select A Huf File");
		         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HUF files (*.Huf)", new String[]{"*.Huf"});
		         fileChooser.getExtensionFilters().add(extFilter);
		         File file = fileChooser.showOpenDialog(primaryStage);
		         if (file != null) {
		             try {
		                 this.readHeader(file.getPath());
		             } catch (FileNotFoundException var10) {
		                 Alert alert = new Alert(AlertType.ERROR);
		                 alert.setTitle("Decompression Error");
		                 alert.setHeaderText((String)null);
		                 alert.setContentText("You didn't select a file.");
		                 Label contentLabel = (Label)alert.getDialogPane().lookup(".content.label");
		                 contentLabel.setFont(Font.font("Roboto Slab ExtraBold", 14.0));
		                 contentLabel.setTextFill(Color.BLACK);
		                 alert.getDialogPane().setStyle("-fx-background-color: #ff9b66;");
		                 Region dialogContentx = (Region)alert.getDialogPane().getChildren().get(2);
		                 alert.show();
		             }
		         } else {
		             Alert alertx = new Alert(AlertType.ERROR);
		             alertx.setTitle("Error");
		             alertx.setHeaderText("Decompression Error");
		             alertx.setHeaderText((String)null);
		             alertx.setContentText("You didn't select a file.");
		             Label contentLabelx = (Label)alertx.getDialogPane().lookup(".content.label");
		             contentLabelx.setFont(Font.font("Roboto Slab ExtraBold", 14.0));
		             contentLabelx.setTextFill(Color.BLACK);
		             alertx.getDialogPane().setStyle("-fx-background-color: #ffab80;");
		             Region dialogContent = (Region)alertx.getDialogPane().getChildren().get(2);
		             alertx.show();
		         }
		     });
		     labelsVB.getChildren().addAll(new Node[]{this.lb0, this.lb1, this.lb2});
		     labelsVB.setAlignment(Pos.CENTER_LEFT);
		     this.aaa1.getChildren().addAll(new Node[]{lb3, textAreaX, back});
		     labelsVB.setStyle("-fx-spacing: 15; -fx-padding: 15;");
		     this.aaa1.setStyle("-fx-spacing: 15; -fx-padding: 7;-fx-background-color: #191919;");
		     sheader.setOnAction((r1) -> {
		         textAreaX.clear();
		         this.aaa1.setAlignment(Pos.CENTER);
		         textAreaX.appendText(this.sba.toString());
		         primaryStage.setMaximized(false);
		         primaryStage.setScene(this.scene1);
		         primaryStage.show();
		     });
		     back.setOnAction((r0) -> {
		         primaryStage.setMaximized(false);
		         primaryStage.setScene(this.scene);
		         primaryStage.show();
		     });
		     
		     primaryStage.setMaximized(false);

		     this.scene.setRoot(layout);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		     primaryStage.setScene(this.scene);
		     primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 private static String getFileExtension(String fileName) {
	     return fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0 ? fileName.substring(fileName.lastIndexOf(".") + 1) : "No extension found";
	 }

	 public static String changeFileExtension(String fileName, String newExtension) {
	     int lastDotIndex = fileName.lastIndexOf(46);
	     if (lastDotIndex != -1) {
	         String var10000 = fileName.substring(0, lastDotIndex);
	         return var10000 + "." + newExtension;
	     } else {
	         return fileName + "." + newExtension;
	     }
	 }

	 private static void decompressFileA(BitInputStream input, BufferedOutputStream output, TNode<freqData> root, long size) throws IOException {
	     TNode<freqData> curr = root;
	     int count = 0;

	     try {
	         int bit;
	         while((bit = input.read()) != -1) {
	             if (bit == 0) {
	                 curr = curr.left;
	             } else {
	                 curr = curr.right;
	             }

	             if (curr.left == null && curr.right == null) {
	                 ++count;
	                 output.write(((freqData)curr.data).ch);
	                 curr = root;
	                 if ((long)count == size) {
	                     Alert alert = new Alert(AlertType.INFORMATION);
	                     alert.setTitle("Message !!!!");
	                     alert.setHeaderText((String)null);
	                     alert.setContentText("The File Has Decompressed Successfully !!!");
	                     Label contentLabel = (Label)alert.getDialogPane().lookup(".content.label");
	                     contentLabel.setFont(Font.font("Roboto Slab ExtraBold", 18.0));
	                     contentLabel.setTextFill(Color.WHITE);
	                     alert.getDialogPane().setStyle("-fx-background-color: #0766ADFF;");
	                     Region dialogContent = (Region)alert.getDialogPane().getChildren().get(2);
	                     alert.show();
	                     break;
	                 }
	             }
	         }

	         output.flush();
	     } catch (IOException var12) {
	         Alert alert = new Alert(AlertType.ERROR);
	         alert.setTitle("Decomp Error");
	         alert.setHeaderText((String)null);
	         alert.setContentText("Decompression");
	         Label contentLabel = (Label)alert.getDialogPane().lookup(".content.label");
	         contentLabel.setFont(Font.font("Roboto Slab ExtraBold", 14.0));
	         contentLabel.setTextFill(Color.BLACK);
	         alert.getDialogPane().setStyle("-fx-background-color: #ffb38f;");
	         Region dialogContent = (Region)alert.getDialogPane().getChildren().get(2);
	         alert.show();
	     }

	 }
	 
	 private static void writeCompressedFile(String inputFile, BitOutputStream output, String[] encodings) {
		    try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile))) {
		        int data;
		        while ((data = input.read()) != -1) {
		            String code = encodings[data & 0xFF];
		            if (code == null) {		              
		                continue; // Skip this character
		            }
		            char[] bits = code.toCharArray();
		            for (char bit : bits) {
		                output.write(bit - '0');
		            }
		        }
		        output.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	
//	 private static void writeCompressedFile(String inputFile, BitOutputStream output, String[] encodings) {
//	     try {
//	         BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));
//
//	         try {
//	             while(true) {
//	                 int data;
//	                 if ((data = input.read()) == -1) {
//	                     output.flush();
//	                     break;
//	                 }
//
//	                 String code = encodings[data & 255];
//	                 char[] var6 = code.toCharArray();
//	                 int var7 = var6.length;
//
//	                 for(int var8 = 0; var8 < var7; ++var8) {
//	                     char bit = var6[var8];
//	                     output.write(bit - 48);
//	                 }
//	             }
//	         } catch (Throwable var11) {
//	             try {
//	                 input.close();
//	             } catch (Throwable var10) {
//	                 var11.addSuppressed(var10);
//	             }
//
//	             throw var11;
//	         }
//
//	         input.close();
//	     } catch (IOException var12) {
//	         var12.printStackTrace();
//	     }
//
//	 }

	 private static void generateHuffmanCodes(TNode<freqData> root, String code, String[] encodings) {
	     if (root != null) {
	         if (root.left == null && root.right == null) {
	             encodings[((freqData)root.data).ch] = code;
	         }

	         generateHuffmanCodes(root.left, code + "0", encodings);
	         generateHuffmanCodes(root.right, code + "1", encodings);
	       
	     }
	 }

	 public static int countLines(StringBuilder stringBuilder) {
	     int count = 1;

	     for(int i = 0; i < stringBuilder.length(); ++i) {
	         if (stringBuilder.charAt(i) == '\n') {
	             ++count;
	         }
	     }

	     return count;
	 }

	 private static int calculateBitLength(String bitSequence) {
	     return bitSequence != null && !bitSequence.isEmpty() ? bitSequence.length() : 0;
	 }

	 void readHeader(String filePath) throws FileNotFoundException {
	     try {
	         BufferedReader reader = new BufferedReader(new FileReader(filePath));

	         try {
	             String fileNameWithExtension = filePath.substring(filePath.lastIndexOf("\\") + 1);
	             BitInputStream input = new BitInputStream(this, new BufferedInputStream(new FileInputStream(filePath)));
	             String path = filePath.substring(0, filePath.lastIndexOf("\\"));
	             int extensionIndex = fileNameWithExtension.lastIndexOf(".");
	             String fileName = fileNameWithExtension.substring(0, extensionIndex);
	             String extension = fileNameWithExtension.substring(extensionIndex + 1);
	             if (extension.equals("Huf")) {
	                 int headerSize = Integer.parseInt(reader.readLine());
	                 long size = Long.parseLong(reader.readLine());
	                 String fileExtension_original = reader.readLine();
	                 StringBuilder tree = new StringBuilder();
	                 this.lineinheader = Integer.parseInt(reader.readLine());

	                 for(int i = 0; i < headerSize; ++i) {
	                     tree.append((char)reader.read());
	                 }

	                 String newFile = path + "\\" + fileName + "." + fileExtension_original;
	                 BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(newFile)));
	                 System.out.println(tree.toString());
	                 this.minHeapSort = new MinHeapSort(1);
	                 this.minHeapSort.insert(MinHeapSort.buildTree(tree.toString().toCharArray()));
	                 input.skipLines(4 + this.lineinheader);
	                 decompressFileA(input, output, this.minHeapSort.getNode(0), size);
	             }
	         } catch (Throwable var17) {
	             try {
	                 reader.close();
	             } catch (Throwable var16) {
	                 var17.addSuppressed(var16);
	             }

	             throw var17;
	         }

	         reader.close();
	     } catch (Exception var18) {
	         System.out.println(var18.toString());
	     }

	 }

	 private StringBuilder writeTreeToFile(String fileExtension, String newFilePath, long size) throws IOException {
	     this.printPreOrderIterative();
	     StringBuilder sb = new StringBuilder();
	     BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(newFilePath));
	     output.write((this.calculateHeaderSize() + "\n").getBytes());
	     sb.append("The Header Size ==> " + this.calculateHeaderSize());
	     output.write(String.valueOf(size).getBytes());
	     sb.append("\nThe Original File Size ==> " + String.valueOf(size));
	     sb.append("\nThe File Extension ==>" + fileExtension + "\n");
	     output.write(("\n" + fileExtension + "\n").getBytes());
	     sb.append("The Huffman Tree ==> " + this.storePreTree);
	     StringBuilder numOfLine = new StringBuilder("" + this.storePreTree);
	     int nu = countLines(numOfLine);
	     output.write(("" + nu + "\n").getBytes());
	     output.write((this.storePreTree + "\n").getBytes());
	     output.close();
	     return sb;
	 }

	 private int calculateHeaderSize() {
	     return this.storePreTree.length();
	 }

	 private void printPreOrderIterative() {
	     generatePreorderHelperit(this.minHeapSort.getNode(0), this.preorderStringBuilder);
	     this.storePreTree = this.preorderStringBuilder.toString();
	 }

	 private static void generatePreorderHelperit(TNode<freqData> root, StringBuilder sb) {
	     Stack<TNode<freqData>> stack = new Stack();
	     stack.push(root);

	     while(!stack.isEmpty()) {
	         TNode<freqData> current = (TNode)stack.pop();
	         if (current == null) {
	             sb.append('〇');
	         } else {
	             sb.append(((freqData)current.data).ch);
	             stack.push(current.right);
	             stack.push(current.left);
	         }
	     }

	 }

	 public StringBuilder returnNumber(TNode<freqData> data, StringBuilder x, char s) {
	     if (data == null) {
	         return null;
	     } else if (s == ((freqData)data.data).ch) {
	         return x;
	     } else {
	         StringBuilder leftResult = this.returnNumber(data.left, (new StringBuilder(x)).append("0"), s);
	         if (leftResult != null) {
	             return leftResult;
	         } else {
	             StringBuilder rightResult = this.returnNumber(data.right, (new StringBuilder(x)).append("1"), s);
	             return rightResult != null ? rightResult : null;
	         }
	     }
	 }

	 public class BitInputStream extends InputStream {
	     private final InputStream input;
	     private int buffer;
	     private int bitsRemaining;

	     public BitInputStream(final Main this$0, InputStream input) {
	         this.input = input;
	         this.buffer = 0;
	         this.bitsRemaining = 0;
	     }

	     public int read() throws IOException {
	         if (this.bitsRemaining == 0) {
	             this.buffer = this.input.read();
	             if (this.buffer == -1) {
	                 return -1;
	             }

	             this.bitsRemaining = 8;
	         }

	         int bit = this.buffer >>> this.bitsRemaining - 1 & 1;
	         --this.bitsRemaining;
	         return bit;
	     }

	     public void skipLines(int linesToSkip) throws IOException {
	         int linesSkipped = 0;

	         while(linesSkipped < linesToSkip) {
	             int data = this.input.read();
	             if (data == -1) {
	                 break;
	             }

	             if (data == 10) {
	                 ++linesSkipped;
	             }
	         }

	     }
	 }

	 static class BitOutputStream extends OutputStream {
	     private final OutputStream output;
	     private int buffer;
	     private int bitsRemaining;

	     public BitOutputStream(OutputStream output) {
	         this.output = output;
	         this.buffer = 0;
	         this.bitsRemaining = 32;
	     }

	     public void write(int bit) throws IOException {
	         if (bit != 0 && bit != 1) {
	             throw new IllegalArgumentException("Bit must be 0 or 1");
	         } else {
	             this.buffer = this.buffer << 1 | bit;
	             --this.bitsRemaining;
	             if (this.bitsRemaining == 0) {
	                 this.writeIntToStream(this.buffer, this.output);
	                 this.buffer = 0;
	                 this.bitsRemaining = 32;
	             }

	         }
	     }

	     public void flush() throws IOException {
	         while(this.bitsRemaining < 32) {
	             this.write(0);
	         }

	         this.output.flush();
	     }

	     private void writeIntToStream(int value, OutputStream output) throws IOException {
	         for(int i = 3; i >= 0; --i) {
	             output.write(value >>> i * 8 & 255);
	         }
	     }
	 }
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
