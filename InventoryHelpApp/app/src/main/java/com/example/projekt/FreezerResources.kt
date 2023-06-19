package com.example.projekt

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.projekt.databinding.FragmentFreezerResourcesBinding
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*
import java.text.SimpleDateFormat
import java.util.Date

class FreezerResources : Fragment() {
    private var _binding : FragmentFreezerResourcesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFreezerResourcesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitFreezerButton.setOnClickListener {
            val ourWB = createWorkbook()
            createExcelFile(ourWB)
            updateCell()
            Toast.makeText( requireContext(), "Poprawnie zapisano dane do pliku:" +
                        " ${mainViewModel.sheetName.value}_1.xlsx\n" +
                        " Lokalizacja pliku: ${requireContext().filesDir}", Toast.LENGTH_SHORT).show()
        }
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

    @SuppressLint("SimpleDateFormat")
    private fun addData(sheet: Sheet) {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = dateFormat.format(currentDate)

        val row1 = sheet.createRow(0)
        val row3 = sheet.createRow(2)
        val row4 = sheet.createRow(3)
        val row5 = sheet.createRow(4)
        val row6 = sheet.createRow(5)
        val row7 = sheet.createRow(6)
        val row8 = sheet.createRow(7)

        createCell(row1, 0, "Raport stanu produktów CHŁODNIA z dnia $formattedDate")
        mergeCells(sheet, 0, 0, 0, 5)

        createCell(row3, 0, "Nazwa produktu")
        mergeCells(sheet, row3.rowNum, row3.rowNum, 0, 1)
        createCell(row3, 2, "Jednostka wagi")
        mergeCells(sheet, row3.rowNum, row3.rowNum, 2, 3)
        createCell(row3, 4, "Wartość")
        mergeCells(sheet, row3.rowNum, row3.rowNum, 4, 5)


        createCell(row4, 0, binding.nameMeat.text.toString())
        mergeCells(sheet, row4.rowNum, row4.rowNum, 0, 1)
        createCell(row4, 2, binding.unitMeat.text.toString())
        mergeCells(sheet, row4.rowNum, row4.rowNum, 2, 3)
        createCell(row4, 4, binding.editMeat.text.toString())
        mergeCells(sheet, row4.rowNum, row4.rowNum, 4, 5)

        createCell(row5, 0, binding.nameBacon.text.toString())
        mergeCells(sheet, row5.rowNum, row5.rowNum, 0, 1)
        createCell(row5, 2, binding.unitBacon.text.toString())
        mergeCells(sheet, row5.rowNum, row5.rowNum, 2, 3)
        createCell(row5, 4, binding.editBacon.text.toString())
        mergeCells(sheet, row5.rowNum, row5.rowNum, 4, 5)

        createCell(row6, 0, binding.nameSalad.text.toString())
        mergeCells(sheet, row6.rowNum, row6.rowNum, 0, 1)
        createCell(row6, 2, binding.unitSalad.text.toString())
        mergeCells(sheet, row6.rowNum, row6.rowNum, 2, 3)
        createCell(row6, 4, binding.editSalad.text.toString())
        mergeCells(sheet, row6.rowNum, row6.rowNum, 4, 5)

        createCell(row7, 0, binding.nameCornsalad.text.toString())
        mergeCells(sheet, row7.rowNum, row7.rowNum, 0, 1)
        createCell(row7, 2, binding.unitCornsalad.text.toString())
        mergeCells(sheet, row7.rowNum, row7.rowNum, 2, 3)
        createCell(row7, 4, binding.editCornsalad.text.toString())
        mergeCells(sheet, row7.rowNum, row7.rowNum, 4, 5)

        createCell(row8, 0, binding.nameRucola.text.toString())
        mergeCells(sheet, row8.rowNum, row8.rowNum, 0, 1)
        createCell(row8, 2, binding.unitRucola.text.toString())
        mergeCells(sheet, row8.rowNum, row8.rowNum, 2, 3)
        createCell(row8, 4, binding.editRucola.text.toString())
        mergeCells(sheet, row8.rowNum, row8.rowNum, 4, 5)
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
        val excelFile = File(ourAppFileDirectory, "${mainViewModel.sheetName.value}_1.xlsx")
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
                return File(ourAppFileDirectory, "${mainViewModel.sheetName.value}_1.xlsx")
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

    private fun mergeCells(sheet: Sheet, firstRow: Int, lastRow: Int, firstCol: Int, lastCol: Int) {
        val region = CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
        sheet.addMergedRegion(region)
    }


}