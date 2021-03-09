package uz.ali.kurstvalyuta.home

import uz.ali.kurstvalyuta.ModelServer.DataModel

interface InterfaceHome {

    interface ViewToPresenter {
        fun loadAllData()

        fun loadDataByDate(date: String)
    }

    interface PresenterToView {

        fun setResponseData(data: DataModel)
    }
}