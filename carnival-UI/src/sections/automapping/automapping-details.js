import { useCallback, useEffect, useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Divider,
  TextField,
  Unstable_Grid2 as Grid,
  Stack
} from '@mui/material';
import { getColumnList, getTableList, saveTemplate } from 'src/api/report-template';
import AutoMappingDialog from './autmapping-dialog';
import { isEmpty, rmProperty } from 'src/utils/CommonUtil';
import { validateHeaderValue } from 'http';


const endpoints = [
  {
    value: 'carnival database',
    label: 'Carnival DB'
  },
  {
    value: 'storage',
    label: 'Azure Storage'
  },
  {
    value: 'event-hubs',
    label: 'Azure event hubs'
  }

];
const initValues = {
  sourceEnd: 'carnival database',
  destinationEnd: 'storage',
  source: 'car_report',
  templateName: '',
  srcfields: [{
    value: '',
    destField: '',
    sourceLimit: '',
    destLimit: '',
    mapping1: '',
  }]
}
let tbList = []
const tbColumnMap = {}
export const AuaoMappingDetails = () => {
  const [columnList, setColumnList] = useState(['k1', 'k2', 'k3'])
  const [destColumnList, setDestColumnList] = useState(['k1', 'k2', 'k3'])
  const [currField, setCurrField] = useState({ ...initValues.srcfields })
  const [open, setOpen] = useState(false)
  const [values, setValues] = useState({ ...initValues })
  const [tableList, setTableList] = useState(['car_report', 'car_report1'])
  const [destTableList, setDestTableList] = useState([])
  useEffect(() => {
    getTableList('carnival').then((res) => {
      if (res.data) {
        tbList = [...res.data]
        setTableList([...res.data])
        values.source = tbList[0]
      }
      tbList.forEach(tb => {
        getColumnList(tb).then((res) => {
          tbColumnMap[tb] = [...res.data]
          if (tb === tbList[0]) {
            values.columnList = tbColumnMap[tbList[0]]
            setColumnList([...tbColumnMap[tb]])
            setValues({ ...values })
          }
        })
      })
    })
  }, [])
  useEffect(() => {
    if (tbColumnMap[values.source]) {
      setColumnList([...tbColumnMap[values.source]])
    } else {
      setColumnList([])
    }
  }, [tableList])
  useEffect(() => {
    if (tbColumnMap[values.dest]) {
      setDestColumnList([...tbColumnMap[values.source]])
    } else {
      setDestColumnList([])
    }
  }, [destTableList])
  const handleClose = (detail) => {
    setOpen(false)
    currField.filters = [...detail.filters]
    setValues({ ...values })
  }
  const handleClickOpen = (srcField) => {
    if (isEmpty(srcField.value) || isEmpty(srcField.destField)) {
      alert("source filed and destination filed is mandatory")
      return
    }
    setOpen(true)
    setCurrField(srcField)
  }
  const deleteField = (i) => {
    values.srcfields.splice(i, 1)
    setValues({ ...values })
  }
  const addField = () => {
    values.srcfields.push({
      value: '',
      destField: '',
      sourceLimit: '',
      destLimit: '',
      mapping1: '',
    })
    setValues({ ...values })
    console.log(values)
  }

  const endCheck = (e, endName, name, setTbList) => {
    if (e.target.name === endName) {
      if (e.target.value === 'carnival database') {
        setTbList([...tbList])
        values[name] = tbList[0]
      } else {
        setTbList([])
        values[name] = ''
      }
    }
  }
  const tbCheck = (event) => {
    if (event.target.name === 'source') {
      let tbList = tbColumnMap[event.target.value]
      tbList ? setColumnList([...tbList]) : setColumnList([])
    } else if (event.target.name === 'destination') {
      tbColumnMap[event.target.value] ? setDestColumnList([...tbColumnMap[event.target.value]]) : setDestColumnList([])
    }
  }
  const handleChange = useCallback(
    (event) => {
      endCheck(event, 'sourceEnd', 'source', setTableList)
      endCheck(event, 'destinationEnd', 'destination', setDestTableList)
      tbCheck(event)
      values[event.target.name] = event.target.value
      setValues(() => ({ ...values }))
    },
    []
  );
  const handleFieldChange = useCallback(
    (idx, f, v) => {
      values.srcfields[idx][f] = v
      setValues(() => ({
        ...values,
      }))
    },
    []
  );

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
    },
    []
  );
  const saveDetails = () => {
    saveTemplate(values).then((res) => {
      console.log(res)
    })
  }
  return (
    <form
      autoComplete="off"
      noValidate
      onSubmit={handleSubmit}
    >
      <Card>
        <AutoMappingDialog open={open} handleClose={handleClose} srcField={currField} />

        <CardContent sx={{ pt: 0 }}>
          <Box sx={{ m: -1.5 }}>

            <Grid
              container
              spacing={3}
            >
              <Grid
                xs={12}
                md={8}>
                <TextField
                  fullWidth
                  label="Template Name"
                  name="templateName"
                  onChange={handleChange}
                  required
                  value={values.templateName}
                />
              </Grid>
              <Grid
                xs={12}
                md={6}
              >
                <TextField
                  fullWidth
                  label="Select source endpoint"
                  name="sourceEnd"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  value={values.sourceEnd}
                >
                  {endpoints.map((option) => (
                    <option
                      key={option.value}
                      value={option.value}
                    >
                      {option.label}
                    </option>
                  ))}
                </TextField>
              </Grid>
              <Grid
                xs={12}
                md={6}
              >
                <TextField
                  fullWidth
                  label="Select destination endpoint"
                  name="destinationEnd"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  value={values.destinationEnd}
                >
                  {endpoints.map((option) => (
                    <option
                      key={option.value}
                      value={option.value}
                    >
                      {option.label}
                    </option>
                  ))}
                </TextField>
              </Grid>
              <Grid
                xs={12}
                md={6}
              >
                {tableList.length === 0 ? (
                  <TextField
                    fullWidth
                    label="Source"
                    name="source"
                    onChange={handleChange}
                    required
                    value={values.source}
                  />
                ) : (
                  <TextField
                    fullWidth
                    label="Select source table"
                    name="source"
                    onChange={handleChange}
                    required
                    select
                    SelectProps={{ native: true }}
                    value={values.source}
                  >
                    {tableList.map((table, idx) => (
                      <option
                        key={idx}
                        value={table}
                      >
                        {table}
                      </option>
                    ))}
                  </TextField>
                )}

              </Grid>
              <Grid
                xs={12}
                md={6}
              >
                {destTableList.length === 0 ? (
                  <TextField
                    fullWidth
                    label="Destination"
                    name="destination"
                    onChange={handleChange}
                    required
                    value={values.destination}
                  />
                ) : (
                  <TextField
                    fullWidth
                    label="Select destination table"
                    name="destination"
                    onChange={handleChange}
                    required
                    select
                    SelectProps={{ native: true }}
                    value={values.destination}
                  >
                    {destTableList.map((table, idx) => (
                      <option
                        key={idx}
                        value={table}
                      >
                        {table}
                      </option>
                    ))}
                  </TextField>
                )}
              </Grid>
              {
                values.srcfields.map((srcField, i) =>
                  <>
                    <Grid
                      xs={12}
                      md={4}
                    >
                      {
                        columnList.length === 0 ?
                          <TextField
                            fullWidth
                            label={"srcField" + (i + 1)}
                            name={'srcField' + (i + 1)}
                            onChange={(e) => handleFieldChange(i, 'value', e.target.value)}
                            required
                            value={values.srcfields[i].value}
                          />
                          :
                          <TextField
                            fullWidth
                            label="Select source column"
                            name={'srcField' + (i + 1)}
                            onChange={(e) => handleFieldChange(i, 'value', e.target.value)}
                            required
                            select
                            SelectProps={{ native: true }}
                            value={srcField.value}
                          >
                            <option value='' />

                            {columnList.map((column, idx) => (
                              <option
                                key={idx}
                                value={column}
                              >
                                {column}
                              </option>
                            ))}
                          </TextField>
                      }

                    </Grid>
                    <Grid
                      xs={12}
                      md={4}
                    >
                      {
                        destColumnList.length === 0 ?
                          <TextField
                            fullWidth
                            label={"destField" + (i + 1)}
                            name={"destField" + (i + 1)}
                            onChange={(e) => handleFieldChange(i, 'destField', e.target.value)}
                            required
                            value={srcField.destField}
                          />
                          :
                          <TextField
                            fullWidth
                            label="Select Destination column"
                            name={'destField' + (i + 1)}
                            onChange={(e) => handleFieldChange(i, 'destField', e.target.value)}
                            required
                            select
                            SelectProps={{ native: true }}
                            value={srcField.destField}
                          >
                            <option value='' />

                            {columnList.map((column, idx) => (
                              <option
                                key={idx}
                                value={column}
                              >
                                {column}
                              </option>
                            ))}
                          </TextField>
                      }

                    </Grid>
                    <Grid
                      xs={12}
                      md={4}
                    >
                      <Stack direction="row" spacing={2}>

                        <Button variant="contained" onClick={() => handleClickOpen(srcField)}>
                          Edit mapping
                        </Button>
                        <Button variant="contained" color='error' onClick={() => deleteField(i)}>
                          delete
                        </Button>

                      </Stack>

                    </Grid>
                  </>
                )
              }

            </Grid>
          </Box>
        </CardContent>
        <Divider />
        <CardActions sx={{ justifyContent: 'flex-end' }}>
          <Stack direction="row" spacing={2}>
            <Button variant="contained" onClick={addField}>
              Add Field
            </Button>
            <Button variant="contained" onClick={saveDetails}>
              Save details
            </Button>
          </Stack>
        </CardActions>
      </Card>
    </form>
  );
};
