import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Slide from '@mui/material/Slide';
import { Box, Card, CardActions, CardContent, Checkbox, FormControlLabel, Grid, Stack, TextField } from '@mui/material';
import { useState } from 'react';
import { useCallback } from 'react';
import { useEffect } from 'react';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});
const conditions = [
  {
    value: 'contains',
    label: 'Contains'
  },
  {
    value: 'eq',
    label: '='
  },
  {
    value: 'neq',
    label: '!='
  }
];
const initFilter = {
  type: 'contains',
  value: ''
}
export default function AutoMappingDialog({ handleClose, open, srcField }) {

  const [detail, setDetail] = useState({
    filters: [{
      ...initFilter
    }]
  })
  useEffect(() => {
    if (open) {
      detail.souceField = srcField.value
      detail.destField = srcField.destField
      if (srcField.filters && srcField.filters.length >= 1) {
        detail.filters = [...srcField.filters]
      } else {
        detail.filters = [{ ...initFilter }]
      }
      setDetail({ ...detail })
    }
  }, [open])
  const handleFilterChange = useCallback(
    (f, v, i) => {
      detail.filters[i][f] = v
      setDetail({ ...detail })
    },
    []
  );
  const addFilter = () => {
    detail.filters.push({ ...initFilter })
    setDetail({ ...detail })
  }
  const delFilter = (i) => {
    detail.filters.splice(i, 1)
    setDetail({ ...detail })
  }


  return (
    <div>
      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
      >
        <AppBar sx={{ position: 'relative' }}>
          <Toolbar>
            <IconButton
              edge="start"
              color="inherit"
              onClick={handleClose}
              aria-label="close"
            >

            </IconButton>
            <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
              Mapping details
            </Typography>
          </Toolbar>
        </AppBar>
        <Card>

          <CardContent sx={{ pt: 0 }}>
            <Box sx={{ m: -1.5, padding: 10 }}>
              <Grid
                container
                spacing={3}
              >
                <Grid
                  item
                  xs={12}
                  md={6}>
                  <TextField
                    fullWidth
                    label="Source Field"
                    name="sourceField"
                    required
                    disabled
                    value={srcField.value}
                  />
                </Grid>
                <Grid
                  item
                  xs={12}
                  md={6}>
                  <TextField
                    fullWidth
                    label="Destination Field"
                    name="destField"
                    required
                    disabled
                    value={srcField.destField}
                  />
                </Grid>
                <Divider />
                {detail.filters.map((filter, idx) =>
                  <Grid
                    item
                    xs={12}
                    md={6}
                  >
                    <Stack direction="row" spacing={2} >
                      <TextField
                        fullWidth
                        label="Please select filter action"
                        name={'filter' + (idx + 1)}
                        onChange={(event) => handleFilterChange('type', event.target.value, idx)}
                        required
                        select
                        SelectProps={{ native: true }}
                        value={filter.type}
                      >
                        {conditions.map((option) => (
                          <option
                            key={option.value}
                            value={option.value}
                          >
                            {option.label}
                          </option>
                        ))}
                      </TextField>
                      <TextField
                        fullWidth
                        label="Please input value for filter"
                        name={"filterValue" + (idx + 1)}
                        onChange={(event) => handleFilterChange('value', event.target.value, idx)}
                        required
                        value={filter.value}
                      />
                      <Grid
                        item
                        xs={12}
                        md={6}
                      >
                        <Button variant="contained" color='error' fullWidth onClick={() => delFilter(idx)}>
                          delete
                        </Button>
                      </Grid>
                    </Stack>
                  </Grid>


                )}




              </Grid>
            </Box>
          </CardContent>
          <Divider />
          <CardActions sx={{ justifyContent: 'flex-end' }}>
            <Stack direction="row" spacing={3}>
              <Button variant="contained" onClick={addFilter}>
                Add Filter
              </Button>
              <Button variant="contained" onClick={() => handleClose(detail)}>
                Save
              </Button>
            </Stack>
          </CardActions>
        </Card>

      </Dialog>
    </div>
  );
}