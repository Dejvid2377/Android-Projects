package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.projekt.databinding.FragmentFreezerResourcesBinding
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*

class FreezerResources : Fragment() {
    private var _binding : FragmentFreezerResourcesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFreezerResourcesBinding.inflate(layoutInflater,container,false)
        val ourWB = createWorkbook()
        createExcelFile(ourWB)
        updateCell()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createWorkbook(): Workbook {
        // Creating a workbook object from the XSSFWorkbook() class
        val ourWorkbook = XSSFWorkbook()

        //Creating a sheet called "statSheet" inside the workbook and then add data to it
        val sheet: Sheet = ourWorkbook.createSheet("statSheet")
        ourWorkbook.createSheet("testSheet")
        addData(sheet)

        return ourWorkbook
    }

    private fun addData(sheet: Sheet) {

        //Creating rows at passed in indices
        val row1 = sheet.createRow(10)
        val row2 = sheet.createRow(11)
        val row3 = sheet.createRow(12)
        val row4 = sheet.createRow(13)
        val row5 = sheet.createRow(14)
        val row6 = sheet.createRow(15)
        val row7 = sheet.createRow(16)
        val row8 = sheet.createRow(17)

        //Adding data to each  cell
        createCell(row1, 0, "Name")
        createCell(row1, 7, "Score")

        createCell(row2, 0, "Mike")
        createCell(row2, 6, "470")

        createCell(row3, 0, "Montessori")
        createCell(row3, 5, "460")

        createCell(row4, 0, "Sandra")
        createCell(row4, 4, "380")

        createCell(row5, 0, "Moringa")
        createCell(row5, 3, "300")

        createCell(row6, 0, "Torres")
        createCell(row6, 2, "270")

        createCell(row7, 0, "McGee")
        createCell(row7, 1, "420")

        createCell(row8, 0, "Gibbs")
        createCell(row8, 1, "510")

    }

    //function for creating a cell.
    private fun createCell(sheetRow: Row, columnIndex: Int, cellValue: String?) {
        //create a cell at a passed in index
        val ourCell = sheetRow.createCell(columnIndex)
        //add the value to it
        ourCell?.setCellValue(cellValue)
    }

    private fun createExcelFile(ourWorkbook: Workbook) {
        val context = requireContext()
        val ourAppFileDirectory = context.filesDir
        if (ourAppFileDirectory != null && !ourAppFileDirectory.exists()) {
            ourAppFileDirectory.mkdirs()
        }
        val excelFile = File(ourAppFileDirectory, "${mainViewModel.sheetName.value}.xlsx")
        try {
            val fileOut = FileOutputStream(excelFile)
            ourWorkbook.write(fileOut)
            fileOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getExcelFile(): File? {
        val context = requireContext()
        val ourAppFileDirectory = context.filesDir
        ourAppFileDirectory?.let {
            if (it.exists()) {
                return File(ourAppFileDirectory, "${mainViewModel.sheetName.value}.xlsx")
            }
        }
        return null
    }

    //function for reading the workbook from the loaded spreadsheet file
    private fun retrieveWorkbook(): Workbook? {

        //Reading the workbook from the loaded spreadsheet file
        getExcelFile()?.let {
            try {

                //Reading it as stream
                val workbookStream = FileInputStream(it)

                //Return the loaded workbook
                return WorkbookFactory.create(workbookStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return null
    }

    //function for selecting the sheet
    private fun selectSheet(): Sheet? {

        //choosing the workbook
        retrieveWorkbook()?.let { workbook ->

            //Checking the existence of a sheet
            if (workbook.numberOfSheets > 0) {

                //Return the first sheet
                return workbook.getSheetAt(0)
            }
        }

        return null
    }

    private fun updateCell() {
        getExcelFile()?.let {
            try {

                //Reading it as stream
                val workbookStream = FileInputStream(it)

                //Return the loaded workbook
                val workbook = WorkbookFactory.create(workbookStream)
                if (workbook.numberOfSheets > 0) {

                    //Return the first sheet
                    val sheet = workbook.getSheetAt(0)
                    //choosing the first row as the headers
                    val nameHeaderCell = sheet.getRow(0).getCell(0)
                    val scoreHeaderCell = sheet.getRow(0).getCell(1)

                    //selecting cells to be editted and formatted
                    val targetCellLabel = sheet.getRow(1).getCell(0)
                    val targetCellValue = sheet.getRow(1).getCell(1)

                    val font: Font = workbook.createFont()
                    val headerCellStyle = workbook.createCellStyle()
                    val targetCellDataStyle = workbook.createCellStyle()

                    //choosing white color and a bold formatting
                    font.color = IndexedColors.WHITE.index
                    font.bold = true

                    //applying formatting styles to the cells
                    headerCellStyle.setAlignment(HorizontalAlignment.LEFT)
                    headerCellStyle.fillForegroundColor = IndexedColors.RED.index
                    headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
                    headerCellStyle.setFont(font);
                    nameHeaderCell.cellStyle = headerCellStyle
                    scoreHeaderCell.cellStyle = headerCellStyle

                    targetCellDataStyle.setAlignment(HorizontalAlignment.LEFT)
                    targetCellValue.cellStyle = targetCellDataStyle
                    targetCellLabel.cellStyle = targetCellDataStyle

                    //feeding in new values to the selected cells
                    targetCellLabel.setCellValue("Mitchelle")
                    targetCellValue.setCellValue(140.0)

                    workbookStream.close()

                    //saving the changes
                    try {
                        val fileOut = FileOutputStream(it)
                        workbook.write(fileOut)
                        fileOut.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteSheet() {
        getExcelFile()?.let {
            try {

                //Reading it as stream
                val workbookStream = FileInputStream(it)

                //Return the loaded workbook
                val workbook = WorkbookFactory.create(workbookStream)
                if (workbook.numberOfSheets > 0) {
                    //removing the second sheet
                    workbook.removeSheetAt(1)
                    workbookStream.close()

                    try {
                        val fileOut = FileOutputStream(it)
                        workbook.write(fileOut)
                        fileOut.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteRow() {
        getExcelFile()?.let {
            try {
                val rowNo = 1
                //Reading it as stream
                val workbookStream = FileInputStream(it)

                //Return the loaded workbook
                val workbook = WorkbookFactory.create(workbookStream)
                if (workbook.numberOfSheets > 0) {

                    //Return the first sheet
                    val sheet = workbook.getSheetAt(0)

                    //getting the total number of rows available
                    val totalNoOfRows = sheet.lastRowNum

                    val targetRow = sheet.getRow(rowNo)
                    if (targetRow != null) {
                        sheet.removeRow(targetRow)
                    }

                    /*excluding the last row, move the cells that come
                    after the deleted row one step behind*/
                    if (rowNo >= 0 && rowNo < totalNoOfRows) {
                        sheet.shiftRows(rowNo + 1, totalNoOfRows, -1)
                    }
                    workbookStream.close()

                    try {
                        val fileOut = FileOutputStream(it)
                        workbook.write(fileOut)
                        fileOut.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteColumn() {
        getExcelFile()?.let {
            try {
                val colNo = 0
                //Reading it as stream
                val workbookStream = FileInputStream(it)

                //Return the loaded workbook
                val workbook = WorkbookFactory.create(workbookStream)
                if (workbook.numberOfSheets > 0) {

                    //Return the first sheet
                    val sheet = workbook.getSheetAt(0)
                    val totalRows = sheet.lastRowNum
                    val row = sheet.getRow(colNo)
                    val maxCell = row.lastCellNum.toInt()
                    if (colNo >= 0 && colNo <= maxCell) {
                        for (rowNo in 0..totalRows) {
                            val targetCol: Cell = sheet.getRow(rowNo).getCell(colNo)
                            if (targetCol != null) {
                                sheet.getRow(rowNo).removeCell(targetCol);
                            }
                        }
                    }
                    workbookStream.close()

                    try {
                        val fileOut = FileOutputStream(it)
                        workbook.write(fileOut)
                        fileOut.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}