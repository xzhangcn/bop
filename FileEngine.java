/******************************************************************************
 *  author:     Rhett Zhang
 *  created:    2020/01/30
 *  Compilation:  javac-algs4 FileEngine.java
 *  Execution:    java-algs4 FileEngine -list/-move foldername filenameExt
 *  Dependencies: StdOut.java
 *
 *  FileEngine is a small utility used to manage files.
 *  It originates from a practical situation I met.
 *  There are many photos and videos sit in my cellphone, but no good tool to
 *  download and organize them into my computer. That really annoys me.
 *
 *  So I decided to write one to resolve this problem.
 *
 *  Its functionality is to organize the files in a directory in the
 *  following way: create the sub-folders according to the month of files'
 *  modification time, and move the files to the folders subsequently.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileEngine {
    private String filenameExt;
    private int nFilesListed;     // total number of listed files
    private int nFilesMoved;

    private FileEngine(String filenameExt) {
        this.filenameExt = filenameExt;
        this.nFilesListed = 0;
        this.nFilesMoved = 0;
    }

    public int getFileListedCount() {
        return this.nFilesListed;
    }

    public int getFileMovedCount() {
        return this.nFilesMoved;
    }

    // recursively list files in a folder
    public void listFilesInFolder(File folder) {
        int count = 0;
        String fileName;

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                // StdOut.printf("file path: %s \n", file.getName());
                listFilesInFolder(file);
            }
            else {
                fileName = file.getName();
                count++;
                this.nFilesListed++;

                StdOut.printf("%s \n", fileName);
            }
        }

        StdOut.printf("total files: %d in the folder: %s \n\n", count, folder.getName());
    }

    // create the sub-folders accordingly to the modification month of the files in the folder;
    // and move the files into the created sub-folders subsequently.
    public void moveFilesInFolder(String folderName) {
        File folder = new File(folderName);

        String fileName;
        String newFolderName;
        String strMonth;
        SET<String> setMonth = new SET<String>();

        for (File file : folder.listFiles(new MyFilter())) {
            if (!file.isDirectory()) {

                fileName = file.getName();
                strMonth = getFileLastModifiedMonth(fileName);
                newFolderName = folderName + "/" + strMonth;

                if (!setMonth.contains(newFolderName)) {
                    setMonth.add(newFolderName);
                    createFolder(newFolderName);
                }

                moveFile(folderName + "/" + fileName, newFolderName + "/" + fileName);
                this.nFilesMoved++;

                StdOut.printf("%s \n", newFolderName + "/" + fileName);
            }
        }

        // StdOut.printf("total %d number of files moved. \n", nFilesMoved);
    }

    private void moveFile(String src, String dst) {
        Path result = null;
        try {
            result = Files.move(Paths.get(src), Paths.get(dst));
        }
        catch (IOException e) {
            StdOut.printf("Exception while moving files: %s \n", e.getMessage());
        }

        if (result != null)
            StdOut.println("File moved successfully!");
        else
            StdOut.println("File movement failed.");
    }

    private String getFileCreationTime(String fileName) {
        File file = new File(fileName);
        String strDate = null;
        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime time = attrs.creationTime();

            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            strDate = simpleDateFormat.format(new Date(time.toMillis()));

            System.out.println("The file creation date and time is: " + strDate);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    private void createFolder(String folderName) {
        File file = new File(folderName);
        boolean isSuccess = false;

        if (!file.exists()) {
            isSuccess = file.mkdir();
            if (isSuccess)
                StdOut.printf("%s successfully created. \n", file.getAbsolutePath());
            else
                StdOut.printf("%s failed creating. \n", file.getAbsolutePath());
        }
        else
            StdOut.printf("%s already exists! \n", file.getAbsolutePath());
    }

    private String getFileLastModifiedMonth(String fileName) {
        File file = new File(fileName);

        // System.out.println("Before Format : " + file.lastModified());

        // SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String strDate = sdf.format(file.lastModified());
        // System.out.println("After Format : " + strDate);

        return strDate;
    }


    private class MyFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(filenameExt);
        }
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String option = args[0];        // list or move options
        String folderName = args[1];
        String filenameExt = args[2];

        FileEngine fileEngine = new FileEngine(filenameExt);
        if (option.equalsIgnoreCase("-list")) {
            File file = new File(folderName);
            fileEngine.listFilesInFolder(file);
            StdOut.printf("total number of files listed: %d \n", fileEngine.getFileListedCount());
        }
        else if (option.equalsIgnoreCase("-move")) {
            fileEngine.moveFilesInFolder(folderName);
            StdOut.printf("total number of files moved: %d \n", fileEngine.getFileMovedCount());
        }
    }
}
