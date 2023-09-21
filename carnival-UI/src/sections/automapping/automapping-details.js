import { useCallback, useState } from 'react';
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
import { getTableList } from 'src/api/report-template';
import AutoMappingDialog from './autmapping-dialog';


const endpoints = [
  {
    value: 'carnival database',
    label: 'Carnival DB'
  },
  {
    value: 'storage',
    label: 'Azure Storage'
  }
];
export const AuaoMappingDetails = () => {
  const [columnList, setColumnList] = useState(['k1', 'k2', 'k3'])
  const [destColumnList, setDestColumnList] = useState(['k1', 'k2', 'k3'])
  const [fieldCnt, setFieldCnt] = useState(3)

  const [open, setOpen] = useState(false)
  const handleClose = () =>  setOpen(false)
  const handleClickOpen = () => setOpen(true)

  const [values, setValues] = useState({
    sourceEnd: 'carnival database',
    destinationEnd: 'storage',
    source: 'car_report',
    srcField1: {
      value: '',
      destField1: '',
      sourceLimit: '',
      destLimit: '',
      mapping1: '',
    }
  });
  const [tableList, setTableList] = useState(['car_report', 'car_report1'])

  const [destTableList, setDestTableList] = useState([])
  const endCheck = (e, endName, name, setTbList) => {
    if (e.target.name === endName) {
      if (e.target.value === 'carnival database') {
        setTbList(['car_report', 'car_report1'])
        values[name] = 'car_report'
      } else {
        setTbList([])
        values[name] = ''
      }
    }
  }
  const handleChange = useCallback(
    (event) => {
      endCheck(event, 'sourceEnd', 'source', setTableList)
      endCheck(event, 'destinationEnd', 'dest', setDestTableList)


      setValues(() => ({
        ...values,
        [event.target.name]: event.target.value
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

  return (
    <form
      autoComplete="off"
      noValidate
      onSubmit={handleSubmit}
    >
      <Card>
        <AutoMappingDialog open={open} handleClose={handleClose}/>
        <CardHeader
          subheader="The information can be edited"
          title="Profile"
        />
        <CardContent sx={{ pt: 0 }}>
          <Box sx={{ m: -1.5 }}>
            <Grid
              container
              spacing={3}
            >
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
                    name="dest"
                    onChange={handleChange}
                    required
                    value={values.dest}
                  />
                ) : (
                  <TextField
                    fullWidth
                    label="Select destination table"
                    name="dest"
                    onChange={handleChange}
                    required
                    select
                    SelectProps={{ native: true }}
                    value={values.dest}
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
                [...Array(fieldCnt)].map((e, i) =>
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
                            name={"srcField" + (i + 1)}
                            onChange={handleChange}
                            required
                            value={values['srcField' + (i + 1)]}
                          />
                          :
                          <TextField
                            fullWidth
                            label="Select source column"
                            name={"srcField" + (i + 1)}
                            onChange={handleChange}
                            required
                            select
                            SelectProps={{ native: true }}
                            value={values['srcField' + (i + 1)]}
                          >
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
                      <TextField
                        fullWidth
                        label={"destField" + (i + 1)}
                        name={"destField" + (i + 1)}
                        onChange={handleChange}
                        required
                        value={values["destField" + (i + 1)]}
                      />
                    </Grid>
                    <Grid
                      xs={12}
                      md={4}
                    >
                      <Stack direction="row" spacing={2}>

                        <Button variant="contained" onClick={handleClickOpen}>
                          Edit mapping
                        </Button>
                        {
                          i == fieldCnt - 1 ? <Button variant="contained" color='error' onClick={() => confirm("aaa")}>
                            delete
                          </Button>
                            : <></>
                        }

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
          <Button variant="contained" onClick={() => confirm("aaa")}>
            Save details
          </Button>
        </CardActions>
      </Card>
    </form>
  );
};
