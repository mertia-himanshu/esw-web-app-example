import { Table, Typography } from 'antd'
import type { ColumnsType } from 'antd/lib/table'
import React, { useEffect, useState } from 'react'
import { useLocationService } from '../../contexts/LocationServiceContext'
import type { RaResponse } from '../../models/Models'
import { fetchSavedRaValues } from '../../utils/api'
import { errorMessage } from '../../utils/message'
import { getBackendUrl } from '../../utils/resolveBackend'

//  #add-columns
const HeaderTitle = ({ title }: { title: string }): JSX.Element => (
  <Typography.Title level={5} style={{ marginBottom: 0 }}>
    {title}
  </Typography.Title>
)

const columns: ColumnsType<RaResponse> = [
  {
    title: <HeaderTitle title={'Formatted Ra Value'} />,
    dataIndex: 'formattedRa',
    key: 'formattedRa'
  }
]
// #add-columns

// #use-fetch
// #add-component
export const RaTable = (): JSX.Element => {
  // #add-component
  const locationService = useLocationService()
  const [raValues, setRaValues] = useState<RaResponse[]>()

  useEffect(() => {
    async function fetchRaValues() {
      const backendUrl = await getBackendUrl(locationService)
      if (backendUrl) {
        const raValues = await fetchSavedRaValues(backendUrl)
        console.error('raValues', raValues)
        setRaValues(raValues)
      } else {
        errorMessage('Failed to fetch ra values')
      }
    }

    fetchRaValues()
  }, [])

  // #add-component
  return (
    <Table
      rowKey={(record) => record.id}
      pagination={false}
      dataSource={raValues}
      columns={columns}
      bordered
    />
  )
  // #add-component
  // #use-fetch

}
