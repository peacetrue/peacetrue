package com.github.peacetrue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author : xiayx
 * @since : 2020-11-27 23:10
 **/
public abstract class FileUtils {

    protected FileUtils() {
    }

    /**
     * replaceExtension("a/b/c.txt","pdf")="a/b/c.pdf"
     */
    public static String replaceExtension(String filePath, String extension) {
        return filePath.replaceFirst("(.*\\.).*$", "$1" + extension);
    }

    public static void unzip(String fileZip) throws IOException {
        unzip(fileZip, Paths.get(fileZip).getParent().toFile());
    }

    public static void unzip(String fileZip, File destDir) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public static boolean isAbsolutePath(String formatPath) {
        return formatPath.startsWith(File.separator);
    }

    public static boolean isRelativePath(String formatPath) {
        return !isAbsolutePath(formatPath);
    }

    public static String formatPath(String path) {
        path = path.replaceAll(File.separator + "{2,}", File.separator);
        return path.endsWith(File.separator) ? path.substring(0, path.length() - 1) : path;
    }

    public static String formatRelativePath(String relativePath) {
        relativePath = formatPath(relativePath);
        return relativePath.startsWith(File.separator) ? relativePath.substring(1) : relativePath;
    }

    public static String concat(String baseFormatPath, String formatPath) {
        if (isAbsolutePath(formatPath)) return baseFormatPath + formatPath;
        return baseFormatPath + File.separator + formatPath;
    }

    /**
     * buildDateRelativePath()="2020/11/20"
     */
    public static String buildDateRelativePath() {
        return DateTimeFormatterUtils.SEPARATOR_DATE_TIME.format(LocalDateTime.now());
    }

    /** Thread unsafe */
    public static Path buildNewPath(Path path) {
        if (Files.notExists(path)) return path;

        String fileName = path.getFileName().toString();
        //TODO handle no extension context
        int index = fileName.lastIndexOf('.');
        String name = fileName.substring(0, index),
                extension = fileName.substring(index + 1),
                newFileName;
        int i = 0;
        do {
            //eg abc.zip -> abc(1).zip
            newFileName = name + "(" + ++i + ")." + extension;
            path = path.resolveSibling(newFileName);
        } while (Files.exists(path));
        return path;
    }

}
