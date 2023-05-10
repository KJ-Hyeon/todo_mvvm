package com.example.todo_mvvm

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.todo_mvvm.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogBinding
    private var startDay: String = ""
    private var endDay: String = ""
    private var listener: BottomSheetFragmentInterface? = null

    interface BottomSheetFragmentInterface {
        fun onButtonClick(startDay: String, endDay: String)
    }

    fun setonDateSelectListener(listener: BottomSheetFragmentInterface) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_dialog, container, false)

        binding.calendarView.addDecorators(BeforedayDecorator())

        binding.calendarView.setOnRangeSelectedListener { widget, dates ->
            startDay = dates[0].date.toString()
            endDay = dates[dates.size - 1].date.toString()
            widget.addDecorator(SelectRangeDayDecorator(dates))
            // bottomSheetDialog를 dismiss 하고, AddFragment의 선택한 날짜를 전달 (Fragment사이의 데이터 전달)
            // dimiss를 하면 너무 바로 없어져서 차라리 button하나를 만드는 방법도 괜찮을듯
            binding.okButton.isEnabled = true
        }

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            // removeDecorator()가 생각과 다르게 작동해서 모든 Deco를 지우고 BeforeDeco를 다시 설정
            widget.removeDecorators()
            widget.addDecorator(BeforedayDecorator())
            widget.addDecorator(SelectDayDecorator(date))
            Log.e("CalendarClick", "$date")
        }

        binding.okButton.setOnClickListener {
            dismiss()
            listener?.onButtonClick(startDay, endDay)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    inner class BeforedayDecorator() : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day.isBefore(CalendarDay.today())
        }

        override fun decorate(view: DayViewFacade?) {
            view?.let {
                it.addSpan(ForegroundColorSpan(Color.GRAY))
                it.setDaysDisabled(true)
            }
        }
    }

    inner class SelectRangeDayDecorator(private val dates: List<CalendarDay>) : DayViewDecorator {

        private var drawable: Drawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.selected_calendar)!!

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade?) {
            view?.setSelectionDrawable(drawable)
        }
    }

    inner class SelectDayDecorator(private val date: CalendarDay) : DayViewDecorator {

        private var drawable: Drawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.selected_calendar)!!

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == date
        }

        override fun decorate(view: DayViewFacade?) {
            view?.setSelectionDrawable(drawable)
        }
    }

}