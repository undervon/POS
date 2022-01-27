import React, { useState, useEffect } from 'react'
import MaterialTable from '@material-table/core'
import './Books.css'

const columns = [
    { field: 'isbn', title: 'ISBN' },
    { field: 'title', title: 'Title' },
    { field: 'publishing', title: 'Publishing' },
    { field: 'publicationYear', title: 'Publication Year' },
    { field: 'genre', title: 'Genre' },
    { field: 'stock', title: 'Stock' },
    { field: 'price', title: 'Price' }
]

const authColumns = [
    { field: 'id', title: 'ID' },
    { field: 'firstName', title: 'First name' },
    { field: 'lastName', title: 'Last name' }
]

const URL = 'http://localhost:8089/api/1.0/bookcollection'

const Books = () => {
    const [data, setData] = useState([])

    const api = require('axios').default

    useEffect(() => {
        api.get(URL + '/books')
            .then(json => { 
                console.log(json.data._embedded.bookDTOList)
                setData(json.data._embedded.bookDTOList)
            })
    }, [])

    const handleRowAdd = (newData, resolve) => {
        api.put(URL + '/books/' + newData.isbn, newData)
            .then(json => {
                let dataToAdd = [...data]
                dataToAdd.push(newData)
                setData(dataToAdd)
                resolve()
            })
    }

    const handleRowUpdate = (newData, oldData, resolve) => {
        api.put(URL + '/books/' + oldData.isbn, newData)
            .then(json => {
                const dataUpdate = [...data]
                const isbn = oldData.tableData.isbn
                dataUpdate[isbn] = newData
                setData([...dataUpdate])
                resolve()
            })
      }

    const handleRowDelete = (oldData, resolve) => {
        api.delete(URL + '/books/' + oldData.isbn)
            .then(json => {
                const dataDelete = [...data]
                const isbn = oldData.tableData.isbn
                dataDelete.splice(isbn, 1)
                setData([...dataDelete])
                resolve()
            })
      }

    return (
        <div className='containerTableAndTitle'>
            <div className='table'>
                <MaterialTable
                    title='Books'
                    columns={columns}
                    data={data}
                    editable={{
                        onRowUpdate: (newData, oldData) =>
                            new Promise((resolve) => {
                                handleRowUpdate(newData, oldData, resolve)
                            }),

                        onRowAdd: (newData) =>
                            new Promise((resolve) => {
                                handleRowAdd(newData, resolve)
                            }),

                        onRowDelete: (oldData) =>
                            new Promise((resolve) => {
                                handleRowDelete(oldData, resolve)
                            })
                    }}
                    detailPanel={rowData => {
                        return (
                            <MaterialTable
                                title='Authors'
                                columns={authColumns}
                                data={data.authorDTOS}
                                options={{
                                    search: false,
                                    paging: false
                                }}
                            />
                        )
                      }}
                />
            </div>
        </div>
    )
}

export default Books